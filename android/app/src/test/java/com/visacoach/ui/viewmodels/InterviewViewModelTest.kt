package com.visacoach.ui.viewmodels

import com.visacoach.data.audio.AudioPlayer
import com.visacoach.data.audio.AudioRecorder
import com.visacoach.data.local.TokenStorage
import com.visacoach.data.remote.AndroidInterviewState
import com.visacoach.data.remote.ServerWsMessage
import com.visacoach.data.remote.WebSocketManager
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InterviewViewModelTest {

    private val webSocketManager: WebSocketManager = mockk(relaxed = true)
    private val audioRecorder: AudioRecorder = mockk(relaxed = true)
    private val audioPlayer: AudioPlayer = mockk(relaxed = true)
    private val tokenStorage: TokenStorage = mockk(relaxed = true)

    private val incomingFlow = MutableSharedFlow<ServerWsMessage>()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InterviewViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { webSocketManager.incomingEvents } returns incomingFlow
        every { tokenStorage.accessToken } returns flowOf("mock-jwt-token")

        viewModel = InterviewViewModel(
            webSocketManager,
            audioRecorder,
            audioPlayer,
            tokenStorage
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testStartInterviewSession_ConnectsWebSocket() = runTest {
        viewModel.startInterviewSession()
        advanceUntilIdle()

        verify { webSocketManager.connect("wss://api.visacoach.co.ke/ws/interview", "mock-jwt-token") }
    }

    @Test
    fun testHandleQuestionStarted_TransitionsToAiSpeaking() = runTest {
        advanceUntilIdle()

        incomingFlow.emit(
            ServerWsMessage(
                event = "QUESTION_STARTED",
                questionNumber = 2,
                questionText = "What is the primary purpose of your trip to the United States?",
                category = "TRAVEL_PURPOSE"
            )
        )
        advanceUntilIdle()

        assertEquals(AndroidInterviewState.AI_SPEAKING, viewModel.uiState.value.state)
        assertEquals(2, viewModel.uiState.value.questionNumber)
        assertEquals("TRAVEL_PURPOSE", viewModel.uiState.value.category)
    }

    @Test
    fun testUserDoneSpeaking_TransitionsToProcessing() = runTest {
        advanceUntilIdle()

        // Force to listening state
        incomingFlow.emit(
            ServerWsMessage(
                event = "QUESTION_STARTED",
                questionNumber = 1,
                questionText = "Why are you traveling?",
                category = "TRAVEL_PURPOSE"
            )
        )
        advanceUntilIdle()
        advanceTimeBy(3000) // Delay triggers transition to listening

        viewModel.onUserDoneSpeaking()
        advanceUntilIdle()

        verify { audioRecorder.stopRecording() }
        verify { webSocketManager.stopSpeaking() }
        assertEquals(AndroidInterviewState.PROCESSING, viewModel.uiState.value.state)
    }

    @Test
    fun testInterviewCompleted_StopsRecordingAndTransitions() = runTest {
        advanceUntilIdle()

        incomingFlow.emit(
            ServerWsMessage(
                event = "INTERVIEW_COMPLETED",
                sessionId = "sess-777",
                message = "Session concluded."
            )
        )
        advanceUntilIdle()

        assertEquals(AndroidInterviewState.COMPLETED, viewModel.uiState.value.state)
        verify { audioPlayer.stop() }
    }
}
