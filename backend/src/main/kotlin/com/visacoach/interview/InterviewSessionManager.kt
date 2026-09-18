package com.visacoach.interview

import com.visacoach.ai.AIInterviewerResponse
import com.visacoach.ai.AIInterviewerService
import com.visacoach.domain.entity.*
import com.visacoach.domain.repository.*
import com.visacoach.evaluation.EvaluationService
import com.visacoach.profile.ProfileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class InterviewSessionManager(
    private val interviewRepository: InterviewRepository,
    private val interviewQuestionRepository: InterviewQuestionRepository,
    private val answerRepository: AnswerRepository,
    private val userRepository: UserRepository,
    private val visaTypeRepository: VisaTypeRepository,
    private val profileService: ProfileService,
    private val aiInterviewerService: AIInterviewerService,
    private val audioStreamService: AudioStreamService,
    private val redisSessionManager: RedisSessionManager,
    private val evaluationService: EvaluationService
) {
    private val logger = LoggerFactory.getLogger(InterviewSessionManager::class.java)
    private val activeSessions = ConcurrentHashMap<String, LiveInterviewSession>()
    private val stateMachines = ConcurrentHashMap<String, InterviewStateMachine>()
    private val sessionScope = CoroutineScope(Dispatchers.IO)

    @Transactional
    fun initializeSession(userId: String, visaTypeCode: String): LiveInterviewSession {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }
        val visaType = visaTypeRepository.findByCode(visaTypeCode)
            .orElseGet { visaTypeRepository.findByCode("B1_B2").orElseThrow() }

        val sessionId = UUID.randomUUID().toString()

        val interview = Interview(
            user = user,
            visaType = visaType,
            sessionId = sessionId,
            status = "IN_PROGRESS",
            startedAt = Instant.now()
        )
        interviewRepository.save(interview)

        val liveSession = LiveInterviewSession(
            sessionId = sessionId,
            userId = userId,
            visaTypeCode = visaTypeCode,
            currentState = InterviewState.CONNECTING
        )
        activeSessions[sessionId] = liveSession
        stateMachines[sessionId] = InterviewStateMachine(InterviewState.CONNECTING)
        redisSessionManager.setLiveState(sessionId, InterviewState.CONNECTING)

        return liveSession
    }

    fun getSession(sessionId: String): LiveInterviewSession? = activeSessions[sessionId]

    fun getStateMachine(sessionId: String): InterviewStateMachine? = stateMachines[sessionId]

    suspend fun conductNextStep(sessionId: String, lastUserTranscript: String? = null): ServerMessage {
        val session = activeSessions[sessionId] ?: return ServerMessage(
            event = ServerEventType.ERROR,
            message = "Session not found or expired."
        )
        val sm = stateMachines[sessionId] ?: InterviewStateMachine(InterviewState.IDLE)

        if (lastUserTranscript != null && session.currentQuestionNumber > 0) {
            // Save answer to previous question
            saveAnswer(sessionId, session.currentQuestionNumber, lastUserTranscript)
            val lastQ = session.conversationHistory.lastOrNull()?.first ?: "Initial question"
            session.conversationHistory.add(Pair(lastQ, lastUserTranscript))
        }

        // Transition to AI_THINKING
        sm.transitionTo(InterviewState.AI_THINKING)
        redisSessionManager.setLiveState(sessionId, InterviewState.AI_THINKING)

        val profile = profileService.getProfile(session.userId)
        val nextAiResponse: AIInterviewerResponse = aiInterviewerService.getNextQuestion(
            profile = profile,
            questionNumber = session.currentQuestionNumber + 1,
            categoriesCovered = session.categoriesCovered,
            history = session.conversationHistory
        )

        if (nextAiResponse.action == "END_INTERVIEW") {
            return completeInterview(sessionId, nextAiResponse.question)
        }

        session.currentQuestionNumber += 1
        session.currentCategory = nextAiResponse.category
        if (!session.categoriesCovered.contains(nextAiResponse.category)) {
            session.categoriesCovered.add(nextAiResponse.category)
        }

        // Save interview question in MySQL
        saveInterviewQuestion(sessionId, session.currentQuestionNumber, nextAiResponse)

        sm.transitionTo(InterviewState.AI_SPEAKING)
        redisSessionManager.setLiveState(sessionId, InterviewState.AI_SPEAKING)

        // Generate synthesized TTS audio
        val audioBytes = audioStreamService.synthesizeSpeech(nextAiResponse.question)
        val base64Audio = Base64.getEncoder().encodeToString(audioBytes)

        return ServerMessage(
            event = ServerEventType.QUESTION_STARTED,
            sessionId = sessionId,
            state = InterviewState.AI_SPEAKING,
            questionNumber = session.currentQuestionNumber,
            questionText = nextAiResponse.question,
            category = nextAiResponse.category,
            audioBase64 = base64Audio
        )
    }

    @Transactional
    fun saveInterviewQuestion(sessionId: String, number: Int, aiResponse: AIInterviewerResponse) {
        val interview = interviewRepository.findBySessionId(sessionId).orElse(null) ?: return
        interview.totalQuestions = number
        interviewRepository.save(interview)

        val iq = InterviewQuestion(
            interview = interview,
            questionText = aiResponse.question,
            category = aiResponse.category,
            sequenceOrder = number,
            isFollowUp = (aiResponse.action == "ASK_FOLLOW_UP")
        )
        interviewQuestionRepository.save(iq)
    }

    @Transactional
    fun saveAnswer(sessionId: String, number: Int, transcript: String) {
        val interview = interviewRepository.findBySessionId(sessionId).orElse(null) ?: return
        val questions = interviewQuestionRepository.findAllByInterviewIdOrderBySequenceOrderAsc(interview.id)
        val currentQ = questions.find { it.sequenceOrder == number } ?: questions.lastOrNull() ?: return

        val answer = Answer(
            interviewQuestion = currentQ,
            transcriptText = transcript,
            durationSeconds = 15
        )
        answerRepository.save(answer)
    }

    @Transactional
    fun completeInterview(sessionId: String, closingRemarks: String): ServerMessage {
        val session = activeSessions.remove(sessionId)
        stateMachines[sessionId]?.forceState(InterviewState.COMPLETED)
        redisSessionManager.setLiveState(sessionId, InterviewState.COMPLETED)

        val interview = interviewRepository.findBySessionId(sessionId).orElse(null)
        if (interview != null) {
            interview.status = "COMPLETED"
            interview.completedAt = Instant.now()
            interviewRepository.save(interview)

            // Trigger Post-Interview Evaluation Engine asynchronously
            sessionScope.launch {
                evaluationService.generateAndPersistEvaluation(interview.id)
            }
        }

        redisSessionManager.removeSession(sessionId)

        return ServerMessage(
            event = ServerEventType.INTERVIEW_COMPLETED,
            sessionId = sessionId,
            state = InterviewState.COMPLETED,
            message = closingRemarks
        )
    }
}
