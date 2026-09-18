package com.visacoach.ai

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface SpeechToTextProvider {
    fun transcribeAudioChunk(audioBytes: ByteArray, isFinal: Boolean): Flow<TranscriptResult>
    suspend fun transcribeFull(audioBytes: ByteArray): String
}

data class TranscriptResult(
    val text: String,
    val isFinal: Boolean,
    val confidence: Double = 0.95
)

interface LargeLanguageModelProvider {
    suspend fun generateCompletion(systemPrompt: String, userPrompt: String): String
    fun streamCompletion(systemPrompt: String, userPrompt: String): Flow<String>
}

interface TextToSpeechProvider {
    suspend fun synthesizeSpeech(text: String): ByteArray
    fun streamSpeechAudio(text: String): Flow<ByteArray>
}

// -----------------------------------------------------------------------------
// Mock Implementations for Local Development & Testing Without Paid AI APIs
// -----------------------------------------------------------------------------

@Component
class MockSpeechToTextProvider : SpeechToTextProvider {
    private val logger = LoggerFactory.getLogger(MockSpeechToTextProvider::class.java)

    override fun transcribeAudioChunk(audioBytes: ByteArray, isFinal: Boolean): Flow<TranscriptResult> = flow {
        if (audioBytes.isNotEmpty()) {
            emit(TranscriptResult("I plan to travel for two weeks to attend a conference and visit tourist sites.", isFinal))
        }
    }

    override suspend fun transcribeFull(audioBytes: ByteArray): String {
        return "I work as a software engineer at a fintech in Nairobi, and I am visiting New York for two weeks on approved vacation leave."
    }
}

@Component
class MockLargeLanguageModelProvider : LargeLanguageModelProvider {
    private val logger = LoggerFactory.getLogger(MockLargeLanguageModelProvider::class.java)

    override suspend fun generateCompletion(systemPrompt: String, userPrompt: String): String {
        // Return structured JSON complying with strict schema
        return """
        {
          "action": "ASK_QUESTION",
          "question": "What specific places or events do you plan to visit while in the United States?",
          "category": "TRAVEL_PURPOSE",
          "difficulty": 1,
          "reason": "Exploring travel purpose detail based on applicant itinerary."
        }
        """.trimIndent()
    }

    override fun streamCompletion(systemPrompt: String, userPrompt: String): Flow<String> = flow {
        val json = generateCompletion(systemPrompt, userPrompt)
        emit(json)
    }
}

@Component
class MockTextToSpeechProvider : TextToSpeechProvider {
    override suspend fun synthesizeSpeech(text: String): ByteArray {
        // Generate placeholder PCM/WAV audio header and silent buffer
        return ByteArray(1024) { 0 }
    }

    override fun streamSpeechAudio(text: String): Flow<ByteArray> = flow {
        emit(synthesizeSpeech(text))
    }
}
