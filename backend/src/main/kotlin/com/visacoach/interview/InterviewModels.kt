package com.visacoach.interview

enum class InterviewState {
    IDLE,
    CONNECTING,
    AI_SPEAKING,
    LISTENING,
    PROCESSING,
    AI_THINKING,
    FOLLOW_UP,
    COMPLETED,
    ERROR
}

enum class ClientEventType {
    AUTHENTICATE,
    START_INTERVIEW,
    AUDIO_START,
    AUDIO_CHUNK,
    AUDIO_END,
    END_INTERVIEW,
    PING
}

enum class ServerEventType {
    AUTHENTICATED,
    INTERVIEW_STARTED,
    AI_SPEAKING,
    AI_AUDIO_START,
    AI_AUDIO_CHUNK,
    AI_AUDIO_END,
    TRANSCRIPT_PARTIAL,
    TRANSCRIPT_FINAL,
    AI_THINKING,
    QUESTION_STARTED,
    QUESTION_COMPLETED,
    INTERVIEW_COMPLETED,
    ERROR,
    PONG
}

data class ClientMessage(
    val event: ClientEventType,
    val token: String? = null,
    val sessionId: String? = null,
    val audioBase64: String? = null,
    val mimeType: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class ServerMessage(
    val event: ServerEventType,
    val sessionId: String? = null,
    val state: InterviewState? = null,
    val message: String? = null,
    val questionNumber: Int? = null,
    val questionText: String? = null,
    val category: String? = null,
    val transcript: String? = null,
    val isFinal: Boolean? = null,
    val audioBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

class InterviewStateMachine(
    private var currentState: InterviewState = InterviewState.IDLE
) {
    fun getCurrentState(): InterviewState = currentState

    @Synchronized
    fun transitionTo(newState: InterviewState): Boolean {
        // Enforce valid state transitions
        val valid = when (currentState) {
            InterviewState.IDLE -> newState in setOf(InterviewState.CONNECTING, InterviewState.ERROR)
            InterviewState.CONNECTING -> newState in setOf(InterviewState.AI_SPEAKING, InterviewState.AI_THINKING, InterviewState.ERROR)
            InterviewState.AI_SPEAKING -> newState in setOf(InterviewState.LISTENING, InterviewState.ERROR, InterviewState.COMPLETED)
            InterviewState.LISTENING -> newState in setOf(InterviewState.PROCESSING, InterviewState.AI_SPEAKING, InterviewState.ERROR)
            InterviewState.PROCESSING -> newState in setOf(InterviewState.AI_THINKING, InterviewState.AI_SPEAKING, InterviewState.ERROR)
            InterviewState.AI_THINKING -> newState in setOf(InterviewState.AI_SPEAKING, InterviewState.FOLLOW_UP, InterviewState.COMPLETED, InterviewState.ERROR)
            InterviewState.FOLLOW_UP -> newState in setOf(InterviewState.AI_SPEAKING, InterviewState.LISTENING, InterviewState.ERROR)
            InterviewState.COMPLETED -> false
            InterviewState.ERROR -> newState == InterviewState.IDLE
        }

        if (valid) {
            currentState = newState
            return true
        }
        return false
    }

    fun forceState(newState: InterviewState) {
        currentState = newState
    }
}
