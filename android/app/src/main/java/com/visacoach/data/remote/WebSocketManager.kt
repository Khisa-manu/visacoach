package com.visacoach.data.remote

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

enum class AndroidInterviewState {
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

data class ClientWsMessage(
    val event: String,
    val token: String? = null,
    val sessionId: String? = null,
    val audioBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class ServerWsMessage(
    val event: String,
    val sessionId: String? = null,
    val state: String? = null,
    val message: String? = null,
    val questionNumber: Int? = null,
    val questionText: String? = null,
    val category: String? = null,
    val transcript: String? = null,
    val isFinal: Boolean? = null,
    val audioBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class WebSocketManager @Inject constructor(
    private val gson: Gson
) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .pingInterval(15, TimeUnit.SECONDS)
        .build()

    private val _incomingEvents = MutableSharedFlow<ServerWsMessage>(extraBufferCapacity = 64)
    val incomingEvents: SharedFlow<ServerWsMessage> = _incomingEvents.asSharedFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun connect(wsUrl: String, authToken: String) {
        disconnect()

        val request = Request.Builder().url(wsUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // Send initial AUTHENTICATE handshake frame
                val authMsg = ClientWsMessage(event = "AUTHENTICATE", token = authToken)
                webSocket.send(gson.toJson(authMsg))
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val serverMsg = gson.fromJson(text, ServerWsMessage::class.java)
                    scope.launch {
                        _incomingEvents.emit(serverMsg)
                    }
                } catch (e: Exception) {
                    // Ignore malformed frames
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                scope.launch {
                    _incomingEvents.emit(
                        ServerWsMessage(
                            event = "ERROR",
                            message = t.localizedMessage ?: "Connection error"
                        )
                    )
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // Connection closed gracefully
            }
        })
    }

    fun startInterview() {
        sendMessage(ClientWsMessage(event = "START_INTERVIEW"))
    }

    fun startSpeaking() {
        sendMessage(ClientWsMessage(event = "AUDIO_START"))
    }

    fun sendAudioChunk(base64Audio: String) {
        sendMessage(ClientWsMessage(event = "AUDIO_CHUNK", audioBase64 = base64Audio))
    }

    fun stopSpeaking() {
        sendMessage(ClientWsMessage(event = "AUDIO_END"))
    }

    fun endInterview() {
        sendMessage(ClientWsMessage(event = "END_INTERVIEW"))
    }

    fun sendPing() {
        sendMessage(ClientWsMessage(event = "PING"))
    }

    private fun sendMessage(msg: ClientWsMessage) {
        val json = gson.toJson(msg)
        webSocket?.send(json)
    }

    fun disconnect() {
        try {
            webSocket?.close(1000, "Normal closure")
        } catch (e: Exception) {
            // Ignored
        }
        webSocket = null
    }
}
