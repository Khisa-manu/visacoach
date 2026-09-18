package com.visacoach.ai

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.visacoach.profile.ProfileDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

data class AIInterviewerResponse(
    val action: String,
    val question: String,
    val category: String,
    val difficulty: Int = 1,
    val reason: String = ""
)

@Service
class AIInterviewerService(
    private val llmProvider: LargeLanguageModelProvider,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(AIInterviewerService::class.java)

    private val allowedActions = setOf("ASK_QUESTION", "ASK_FOLLOW_UP", "MOVE_TO_NEXT_CATEGORY", "END_INTERVIEW")
    private val allowedCategories = setOf(
        "TRAVEL_PURPOSE", "TRIP_DURATION", "EMPLOYMENT", "FINANCES",
        "FAMILY", "TRAVEL_HISTORY", "ACCOMMODATION", "RETURN_PLANS",
        "PREVIOUS_VISA", "SPONSOR"
    )

    suspend fun getNextQuestion(
        profile: ProfileDto,
        questionNumber: Int,
        categoriesCovered: List<String>,
        history: List<Pair<String, String>>
    ): AIInterviewerResponse {
        val systemPrompt = PromptBuilder.buildSystemPrompt()
        val userPrompt = PromptBuilder.buildUserPrompt(profile, questionNumber, categoriesCovered, history)

        var attempts = 0
        val maxAttempts = 3
        while (attempts < maxAttempts) {
            attempts++
            try {
                val rawResponse = llmProvider.generateCompletion(systemPrompt, userPrompt)
                val parsed = parseAndValidate(rawResponse)
                if (parsed != null) {
                    return parsed
                }
            } catch (e: Exception) {
                logger.warn("AI generation attempt $attempts failed: ${e.message}")
            }
        }

        // Deterministic safe fallback adhering to rules
        return getSafeFallbackResponse(questionNumber, categoriesCovered)
    }

    fun parseAndValidate(rawText: String): AIInterviewerResponse? {
        val cleaned = rawText.trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        return try {
            val response: AIInterviewerResponse = objectMapper.readValue(cleaned)

            val actionValid = response.action in allowedActions
            val categoryValid = response.category in allowedCategories
            val questionValid = response.question.isNotBlank() && response.question.length < 500

            if (actionValid && categoryValid && questionValid) {
                response
            } else {
                logger.warn("AI output validation failed schema check: $cleaned")
                null
            }
        } catch (e: Exception) {
            logger.warn("Failed to parse AI JSON response: ${e.message}")
            null
        }
    }

    private fun getSafeFallbackResponse(questionNumber: Int, categoriesCovered: List<String>): AIInterviewerResponse {
        val remainingCategories = allowedCategories.filterNot { categoriesCovered.contains(it) }

        if (questionNumber >= 8 || remainingCategories.isEmpty()) {
            return AIInterviewerResponse(
                action = "END_INTERVIEW",
                question = "Thank you for answering all the questions today. That concludes this mock interview session.",
                category = "RETURN_PLANS",
                difficulty = 1,
                reason = "Target question threshold reached."
            )
        }

        val nextCategory = remainingCategories.firstOrNull() ?: "EMPLOYMENT"
        val (question, diff) = when (nextCategory) {
            "TRAVEL_PURPOSE" -> Pair("What is the primary purpose of your trip to the United States?", 1)
            "TRIP_DURATION" -> Pair("How long are you planning to remain in the U.S., and why this duration?", 1)
            "EMPLOYMENT" -> Pair("Could you describe your current employment and position in Kenya?", 1)
            "FINANCES" -> Pair("Who is covering the expenses for this trip, and how was it budgeted?", 2)
            "FAMILY" -> Pair("Do you have any family members or close relatives currently in the U.S.?", 1)
            "TRAVEL_HISTORY" -> Pair("Have you traveled outside Kenya before, and did you return on schedule?", 1)
            "ACCOMMODATION" -> Pair("Where will you be staying while you are in the United States?", 1)
            "RETURN_PLANS" -> Pair("What strong ties will ensure your timely return to Kenya?", 2)
            "PREVIOUS_VISA" -> Pair("Have you previously applied for any U.S. or foreign visas?", 1)
            "SPONSOR" -> Pair("If someone is sponsoring you, what is their relation to you?", 1)
            else -> Pair("What are your primary travel plans while in the country?", 1)
        }

        return AIInterviewerResponse(
            action = "ASK_QUESTION",
            question = question,
            category = nextCategory,
            difficulty = diff,
            reason = "Fallback sequential progression"
        )
    }
}
