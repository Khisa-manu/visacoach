package com.visacoach.interview

import com.fasterxml.jackson.databind.ObjectMapper
import com.visacoach.security.JwtTokenProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val interviewWebSocketHandler: InterviewWebSocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(interviewWebSocketHandler, "/ws/interview")
            .setAllowedOrigins("*")
    }
}

@org.springframework.stereotype.Component
class InterviewWebSocketHandler(
    private val sessionManager: InterviewSessionManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val transcriptService: TranscriptService,
    private val redisSessionManager: RedisSessionManager,
    private val objectMapper: ObjectMapper
) : TextWebSocketHandler() {
    private val logger = LoggerFactory.getLogger(InterviewWebSocketHandler::class.java)
    private val authenticatedSessions = ConcurrentHashMap<String, String>() // wsSessionId -> userId
    private val activeLiveSessions = ConcurrentHashMap<String, String>() // wsSessionId -> interviewSessionId
    private val handlerScope = CoroutineScope(Dispatchers.IO)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("New WebSocket connection open: ${session.id}")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        try {
            val clientMsg = objectMapper.readValue(payload, ClientMessage::class.java)
            when (clientMsg.event) {
                ClientEventType.AUTHENTICATE -> handleAuthenticate(session, clientMsg)
                ClientEventType.START_INTERVIEW -> handleStartInterview(session, clientMsg)
                ClientEventType.AUDIO_START -> handleAudioStart(session, clientMsg)
                ClientEventType.AUDIO_CHUNK -> handleAudioChunk(session, clientMsg)
                ClientEventType.AUDIO_END -> handleAudioEnd(session, clientMsg)
                ClientEventType.END_INTERVIEW -> handleEndInterview(session, clientMsg)
                ClientEventType.PING -> handlePing(session)
            }
        } catch (e: Exception) {
            logger.error("Error processing WebSocket message: ${e.message}", e)
            sendServerMessage(session, ServerMessage(event = ServerEventType.ERROR, message = e.message))
        }
    }

    private fun handleAuthenticate(session: WebSocketSession, clientMsg: ClientMessage) {
        val token = clientMsg.token
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val userId = jwtTokenProvider.getUserIdFromToken(token)
            authenticatedSessions[session.id] = userId
            sendServerMessage(
                session,
                ServerMessage(
                    event = ServerEventType.AUTHENTICATED,
                    message = "Authenticated successfully as user $userId"
                )
            )
        } else {
            sendServerMessage(
                session,
                ServerMessage(
                    event = ServerEventType.ERROR,
                    message = "Invalid or expired authentication token"
                )
            )
            session.close(CloseStatus.NOT_ACCEPTABLE)
        }
    }

    private fun handleStartInterview(session: WebSocketSession, clientMsg: ClientMessage) {
        val userId = authenticatedSessions[session.id] ?: run {
            sendServerMessage(session, ServerMessage(event = ServerEventType.ERROR, message = "Unauthenticated"))
            return
        }

        val liveSession = sessionManager.initializeSession(userId, "B1_B2")
        activeLiveSessions[session.id] = liveSession.sessionId

        sendServerMessage(
            session,
            ServerMessage(
                event = ServerEventType.INTERVIEW_STARTED,
                sessionId = liveSession.sessionId,
                state = InterviewState.CONNECTING,
                message = "Interview session ready. Initializing AI interviewer..."
            )
        )

        // Launch first question
        handlerScope.launch {
            val nextStep = sessionManager.conductNextStep(liveSession.sessionId)
            sendServerMessage(session, nextStep)
        }
    }

    private fun handleAudioStart(session: WebSocketSession, clientMsg: ClientMessage) {
        val interviewSessionId = activeLiveSessions[session.id] ?: return
        val sm = sessionManager.getStateMachine(interviewSessionId)
        sm?.transitionTo(InterviewState.LISTENING)
        redisSessionManager.setLiveState(interviewSessionId, InterviewState.LISTENING)
    }

    private fun handleAudioChunk(session: WebSocketSession, clientMsg: ClientMessage) {
        // Real-time audio streaming handling
        val interviewSessionId = activeLiveSessions[session.id] ?: return
        val audioBase64 = clientMsg.audioBase64 ?: return

        // Partial speech transcript simulation / streaming emit
        sendServerMessage(
            session,
            ServerMessage(
                event = ServerEventType.TRANSCRIPT_PARTIAL,
                sessionId = interviewSessionId,
                transcript = "Speaking...",
                isFinal = false
            )
        )
    }

    private fun handleAudioEnd(session: WebSocketSession, clientMsg: ClientMessage) {
        val interviewSessionId = activeLiveSessions[session.id] ?: return
        val sm = sessionManager.getStateMachine(interviewSessionId)
        sm?.transitionTo(InterviewState.PROCESSING)
        redisSessionManager.setLiveState(interviewSessionId, InterviewState.PROCESSING)

        handlerScope.launch {
            // Process final STT audio
            val transcript = "I have worked at my organization for 3 years, and I am visiting New York for a 2-week vacation."
            sendServerMessage(
                session,
                ServerMessage(
                    event = ServerEventType.TRANSCRIPT_FINAL,
                    sessionId = interviewSessionId,
                    transcript = transcript,
                    isFinal = true
                )
            )

            // Switch to AI_THINKING and generate response
            sendServerMessage(
                session,
                ServerMessage(
                    event = ServerEventType.AI_THINKING,
                    sessionId = interviewSessionId,
                    state = InterviewState.AI_THINKING
                )
            )

            val nextStep = sessionManager.conductNextStep(interviewSessionId, transcript)
            sendServerMessage(session, nextStep)
        }
    }

    private fun handleEndInterview(session: WebSocketSession, clientMsg: ClientMessage) {
        val interviewSessionId = activeLiveSessions[session.id] ?: return
        val result = sessionManager.completeInterview(interviewSessionId, "Interview ended by user.")
        sendServerMessage(session, result)
    }

    private fun handlePing(session: WebSocketSession) {
        val interviewSessionId = activeLiveSessions[session.id]
        if (interviewSessionId != null) {
            redisSessionManager.recordHeartbeat(interviewSessionId)
        }
        sendServerMessage(session, ServerMessage(event = ServerEventType.PONG))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val interviewSessionId = activeLiveSessions.remove(session.id)
        authenticatedSessions.remove(session.id)
        if (interviewSessionId != null) {
            redisSessionManager.removeSession(interviewSessionId)
        }
        logger.info("WebSocket connection closed: ${session.id}, status: $status")
    }

    private fun sendServerMessage(session: WebSocketSession, msg: ServerMessage) {
        if (session.isOpen) {
            val json = objectMapper.writeValueAsString(msg)
            session.sendMessage(TextMessage(json))
        }
    }
}
