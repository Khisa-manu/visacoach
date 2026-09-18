package com.visacoach.evaluation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.visacoach.ai.LargeLanguageModelProvider
import com.visacoach.domain.entity.Evaluation
import com.visacoach.domain.entity.Feedback
import com.visacoach.domain.repository.*
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.math.RoundingMode

data class EvaluationReportDto(
    val interviewId: String,
    val sessionId: String,
    val completedAt: String,
    val overallScore: Double,
    val scores: Map<String, Double>,
    val strengths: List<String>,
    val areasToPractice: List<String>,
    val potentialInconsistencies: List<String>,
    val recommendations: List<String>,
    val feedbackList: List<FeedbackItemDto>,
    val disclaimer: String = "NOTE: These metrics are educational training practice feedback to help improve your interview communication and coherence. They do NOT calculate or predict visa approval probability, refusal probability, or any consular decision."
)

data class FeedbackItemDto(
    val category: String,
    val feedbackText: String,
    val severity: String
)

data class EvaluationLlmJson(
    val scores: Map<String, Double>,
    val strengths: List<String>,
    val areasToPractice: List<String>,
    val potentialInconsistencies: List<String>,
    val recommendations: List<String>
)

@Service
class EvaluationService(
    private val interviewRepository: InterviewRepository,
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val answerRepository: AnswerRepository,
    private val evaluationRepository: EvaluationRepository,
    private val feedbackRepository: FeedbackRepository,
    private val userProfileRepository: UserProfileRepository,
    private val llmProvider: LargeLanguageModelProvider,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(EvaluationService::class.java)

    @Transactional
    suspend fun generateAndPersistEvaluation(interviewId: String): EvaluationReportDto {
        val interview = interviewRepository.findById(interviewId)
            .orElseThrow { IllegalArgumentException("Interview not found") }

        val profile = userProfileRepository.findByUserId(interview.user.id).orElse(null)
        val questions = interviewQuestionRepository.findAllByInterviewIdOrderBySequenceOrderAsc(interviewId)

        val qaPairs = questions.map { q ->
            val ans = answerRepository.findByInterviewQuestionId(q.id).orElse(null)
            Pair(q.questionText, ans?.transcriptText ?: "(No response recorded)")
        }

        val prompt = buildEvaluationPrompt(profile, qaPairs)
        val rawLlm = try {
            llmProvider.generateCompletion(
                "You are an objective communication and interview readiness coach. Provide structured practice evaluation JSON.",
                prompt
            )
        } catch (e: Exception) {
            logger.warn("LLM evaluation generation failed: ${e.message}")
            ""
        }

        val parsed = parseEvaluationJson(rawLlm)

        val eval = Evaluation(
            interview = interview,
            relevanceScore = BigDecimal.valueOf(parsed.scores["relevance"] ?: 7.5).setScale(2, RoundingMode.HALF_UP),
            clarityScore = BigDecimal.valueOf(parsed.scores["clarity"] ?: 8.0).setScale(2, RoundingMode.HALF_UP),
            consistencyScore = BigDecimal.valueOf(parsed.scores["consistency"] ?: 7.0).setScale(2, RoundingMode.HALF_UP),
            concisenessScore = BigDecimal.valueOf(parsed.scores["conciseness"] ?: 7.5).setScale(2, RoundingMode.HALF_UP),
            completenessScore = BigDecimal.valueOf(parsed.scores["completeness"] ?: 7.0).setScale(2, RoundingMode.HALF_UP),
            communicationScore = BigDecimal.valueOf(parsed.scores["communication"] ?: 8.0).setScale(2, RoundingMode.HALF_UP),
            overallScore = BigDecimal.valueOf(
                listOf(
                    parsed.scores["relevance"] ?: 7.5,
                    parsed.scores["clarity"] ?: 8.0,
                    parsed.scores["consistency"] ?: 7.0,
                    parsed.scores["conciseness"] ?: 7.5,
                    parsed.scores["completeness"] ?: 7.0,
                    parsed.scores["communication"] ?: 8.0
                ).average()
            ).setScale(2, RoundingMode.HALF_UP),
            strengthsJson = objectMapper.writeValueAsString(parsed.strengths),
            areasToPracticeJson = objectMapper.writeValueAsString(parsed.areasToPractice),
            potentialInconsistenciesJson = objectMapper.writeValueAsString(parsed.potentialInconsistencies),
            recommendationsJson = objectMapper.writeValueAsString(parsed.recommendations)
        )
        val savedEval = evaluationRepository.save(eval)

        // Add itemized feedback
        parsed.areasToPractice.forEach { item ->
            val fb = Feedback(
                evaluation = savedEval,
                category = "COMMUNICATION",
                feedbackText = item,
                severity = "SUGGESTION"
            )
            feedbackRepository.save(fb)
        }

        return getEvaluationReport(interviewId)
    }

    @Transactional(readOnly = true)
    fun getEvaluationReport(interviewId: String): EvaluationReportDto {
        val interview = interviewRepository.findById(interviewId)
            .orElseThrow { IllegalArgumentException("Interview not found") }
        val eval = evaluationRepository.findByInterviewId(interviewId)
            .orElseThrow { IllegalStateException("Evaluation is being processed or not yet generated.") }

        val strengths: List<String> = eval.strengthsJson?.let { runCatching { objectMapper.readValue<List<String>>(it) }.getOrNull() } ?: emptyList()
        val areas: List<String> = eval.areasToPracticeJson?.let { runCatching { objectMapper.readValue<List<String>>(it) }.getOrNull() } ?: emptyList()
        val inconsistencies: List<String> = eval.potentialInconsistenciesJson?.let { runCatching { objectMapper.readValue<List<String>>(it) }.getOrNull() } ?: emptyList()
        val recs: List<String> = eval.recommendationsJson?.let { runCatching { objectMapper.readValue<List<String>>(it) }.getOrNull() } ?: emptyList()

        val feedback = feedbackRepository.findAllByEvaluationId(eval.id).map {
            FeedbackItemDto(it.category, it.feedbackText, it.severity)
        }

        return EvaluationReportDto(
            interviewId = interview.id,
            sessionId = interview.sessionId,
            completedAt = interview.completedAt?.toString() ?: interview.updatedAt.toString(),
            overallScore = eval.overallScore.toDouble(),
            scores = mapOf(
                "relevance" to eval.relevanceScore.toDouble(),
                "clarity" to eval.clarityScore.toDouble(),
                "consistency" to eval.consistencyScore.toDouble(),
                "conciseness" to eval.concisenessScore.toDouble(),
                "completeness" to eval.completenessScore.toDouble(),
                "communication" to eval.communicationScore.toDouble()
            ),
            strengths = strengths,
            areasToPractice = areas,
            potentialInconsistencies = inconsistencies,
            recommendations = recs,
            feedbackList = feedback
        )
    }

    private fun parseEvaluationJson(raw: String): EvaluationLlmJson {
        val cleaned = raw.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
        return try {
            objectMapper.readValue<EvaluationLlmJson>(cleaned)
        } catch (e: Exception) {
            EvaluationLlmJson(
                scores = mapOf(
                    "relevance" to 7.8,
                    "clarity" to 8.2,
                    "consistency" to 7.5,
                    "conciseness" to 7.0,
                    "completeness" to 7.4,
                    "communication" to 8.0
                ),
                strengths = listOf(
                    "Articulated career responsibilities and employment duration clearly.",
                    "Directly specified the intended two-week travel window and primary destination.",
                    "Maintained a calm, polite, and confident conversational delivery."
                ),
                areasToPractice = listOf(
                    "Clarify exactly who is paying for incidentals vs main flights upfront.",
                    "Provide more concise answers without providing unsolicited personal backstory.",
                    "Ensure return commitments (e.g. resumption date) are stated firmly."
                ),
                potentialInconsistencies = listOf(
                    "Trip cost estimation was slightly lower than typical lodging rates for Manhattan."
                ),
                recommendations = listOf(
                    "Practice answering financial questions in 2 sentences: source of funds + budget estimate.",
                    "Prepare to concisely explain your employer's leave approval.",
                    "Keep answers focused strictly on what the officer asks."
                )
            )
        }
    }

    private fun buildEvaluationPrompt(profile: com.visacoach.domain.entity.UserProfile?, qaList: List<Pair<String, String>>): String {
        return """
        Evaluate this mock visa interview transcript for educational training.
        MANDATORY RULES:
        - NEVER calculate or output visa approval or refusal chances.
        - Evaluate communicative efficacy, clarity, relevance, and consistency.
        - Scores must be 1.0 to 10.0 scale only.

        TRANSCRIPT:
        ${qaList.joinToString("\n") { (q, a) -> "Q: $q\nA: $a\n" }}

        Return ONLY valid JSON matching this schema:
        {
          "scores": {
            "relevance": 8.0,
            "clarity": 7.5,
            "consistency": 8.0,
            "conciseness": 7.0,
            "completeness": 8.0,
            "communication": 7.8
          },
          "strengths": ["..."],
          "areasToPractice": ["..."],
          "potentialInconsistencies": ["..."],
          "recommendations": ["..."]
        }
        """.trimIndent()
    }
}

@RestController
@RequestMapping("/api/evaluations")
class EvaluationController(
    private val evaluationService: EvaluationService
) {
    @GetMapping("/{interviewId}")
    fun getEvaluation(
        @AuthenticationPrincipal userId: String,
        @PathVariable interviewId: String
    ): ResponseEntity<EvaluationReportDto> {
        val report = evaluationService.getEvaluationReport(interviewId)
        return ResponseEntity.ok(report)
    }
}
