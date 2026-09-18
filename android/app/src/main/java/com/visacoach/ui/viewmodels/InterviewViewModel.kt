package com.visacoach.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visacoach.data.audio.AudioPlayer
import com.visacoach.data.audio.AudioRecorder
import com.visacoach.data.local.TokenStorage
import com.visacoach.data.remote.AndroidInterviewState
import com.visacoach.data.remote.ServerWsMessage
import com.visacoach.data.remote.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RealTimeInterviewUiState(
    val state: AndroidInterviewState = AndroidInterviewState.CONNECTING,
    val sessionId: String? = null,
    val questionNumber: Int = 1,
    val totalQuestionsEstimated: Int = 7,
    val category: String = "TRAVEL_PURPOSE",
    val currentQuestionText: String = "Connecting to AI interviewer...",
    val userTranscript: String = "",
    val isMicActive: Boolean = false,
    val elapsedTimeSeconds: Int = 0,
    val connectionStatus: String = "Connecting...",
    val isConnected: Boolean = false,
    val audioAmplitudes: List<Float> = listOf(0.2f, 0.5f, 0.8f, 0.4f, 0.6f, 0.9f, 0.3f),
    val interviewCompletedId: String? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(RealTimeInterviewUiState())
    val uiState: StateFlow<RealTimeInterviewUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var waveformJob: Job? = null

    init {
        observeWebSocketEvents()
    }

    fun startInterviewSession() {
        viewModelScope.launch {
            val token = tokenStorage.accessToken.firstOrNull() ?: "demo-token"
            _uiState.value = RealTimeInterviewUiState(
                state = AndroidInterviewState.CONNECTING,
                connectionStatus = "Securing WebSocket handshake..."
            )
            webSocketManager.connect("wss://api.visacoach.co.ke/ws/interview", token)
            startTimer()
            startWaveformSimulator()
        }
    }

    private fun observeWebSocketEvents() {
        viewModelScope.launch {
            webSocketManager.incomingEvents.collect { msg ->
                handleServerMessage(msg)
            }
        }
    }

    private fun handleServerMessage(msg: ServerWsMessage) {
        when (msg.event) {
            "AUTHENTICATED" -> {
                _uiState.value = _uiState.value.copy(
                    connectionStatus = "Authenticated. Initializing interviewer...",
                    isConnected = true
                )
                webSocketManager.startInterview()
            }
            "INTERVIEW_STARTED" -> {
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.AI_THINKING,
                    sessionId = msg.sessionId,
                    connectionStatus = "AI Interviewer Active"
                )
            }
            "QUESTION_STARTED" -> {
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.AI_SPEAKING,
                    questionNumber = msg.questionNumber ?: _uiState.value.questionNumber,
                    currentQuestionText = msg.questionText ?: "",
                    category = msg.category ?: "TRAVEL_PURPOSE",
                    userTranscript = "",
                    isMicActive = false
                )

                // Play synthesized TTS voice audio
                if (!msg.audioBase64.isNullOrBlank()) {
                    audioPlayer.playBase64Audio(msg.audioBase64) {
                        // AI finished speaking, auto-transition to LISTENING
                        transitionToListening()
                    }
                } else {
                    // Simulated speaking delay if mock audio empty
                    viewModelScope.launch {
                        delay(2500)
                        transitionToListening()
                    }
                }
            }
            "TRANSCRIPT_PARTIAL" -> {
                _uiState.value = _uiState.value.copy(
                    userTranscript = msg.transcript ?: ""
                )
            }
            "TRANSCRIPT_FINAL" -> {
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.PROCESSING,
                    userTranscript = msg.transcript ?: _uiState.value.userTranscript
                )
            }
            "AI_THINKING" -> {
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.AI_THINKING,
                    isMicActive = false
                )
            }
            "INTERVIEW_COMPLETED" -> {
                stopTimer()
                audioRecorder.stopRecording()
                audioPlayer.stop()
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.COMPLETED,
                    interviewCompletedId = _uiState.value.sessionId ?: "mock-session-id"
                )
            }
            "ERROR" -> {
                _uiState.value = _uiState.value.copy(
                    state = AndroidInterviewState.ERROR,
                    errorMessage = msg.message ?: "An unexpected error occurred."
                )
            }
        }
    }

    private fun transitionToListening() {
        _uiState.value = _uiState.value.copy(
            state = AndroidInterviewState.LISTENING,
            isMicActive = true
        )
        webSocketManager.startSpeaking()
        audioRecorder.startRecording { chunk ->
            webSocketManager.sendAudioChunk(chunk)
        }
    }

    fun onUserDoneSpeaking() {
        if (_uiState.value.state == AndroidInterviewState.LISTENING) {
            audioRecorder.stopRecording()
            _uiState.value = _uiState.value.copy(
                state = AndroidInterviewState.PROCESSING,
                isMicActive = false
            )
            webSocketManager.stopSpeaking()
        }
    }

    fun endInterview() {
        webSocketManager.endInterview()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var sec = 0
            while (isActive) {
                delay(1000)
                sec++
                _uiState.value = _uiState.value.copy(elapsedTimeSeconds = sec)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun startWaveformSimulator() {
        waveformJob?.cancel()
        waveformJob = viewModelScope.launch {
            while (isActive) {
                delay(120)
                if (_uiState.value.state == AndroidInterviewState.AI_SPEAKING || _uiState.value.state == AndroidInterviewState.LISTENING) {
                    val randomized = List(7) { (0.15f + Math.random().toFloat() * 0.85f) }
                    _uiState.value = _uiState.value.copy(audioAmplitudes = randomized)
                } else {
                    _uiState.value = _uiState.value.copy(audioAmplitudes = List(7) { 0.15f })
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        waveformJob?.cancel()
        audioRecorder.stopRecording()
        audioPlayer.stop()
        webSocketManager.disconnect()
    }
}
