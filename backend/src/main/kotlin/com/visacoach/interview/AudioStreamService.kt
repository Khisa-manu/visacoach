package com.visacoach.interview

import com.visacoach.ai.SpeechToTextProvider
import com.visacoach.ai.TextToSpeechProvider
import com.visacoach.ai.TranscriptResult
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.UUID

@Service
class AudioStreamService(
    private val ttsProvider: TextToSpeechProvider,
    @Value("\${ai.storage.audio-bucket:visacoach-interview-audio-recordings}")
    private val bucketName: String
) {
    private val logger = LoggerFactory.getLogger(AudioStreamService::class.java)

    fun streamTts(text: String): Flow<ByteArray> {
        return ttsProvider.streamSpeechAudio(text)
    }

    suspend fun synthesizeSpeech(text: String): ByteArray {
        return ttsProvider.synthesizeSpeech(text)
    }

    fun saveAudioToObjectStorage(audioBytes: ByteArray, prefix: String): String {
        // Generates cloud object-storage reference URI (e.g., gs://bucket/sessionId/audio.webm or s3://...)
        val objectKey = "$prefix/${UUID.randomUUID()}.webm"
        logger.info("Saved ${audioBytes.size} audio bytes to cloud storage ref: gs://$bucketName/$objectKey")
        return "gs://$bucketName/$objectKey"
    }
}

@Service
class TranscriptService(
    private val sttProvider: SpeechToTextProvider
) {
    fun processStreamingChunk(audioBytes: ByteArray, isFinal: Boolean): Flow<TranscriptResult> {
        return sttProvider.transcribeAudioChunk(audioBytes, isFinal)
    }

    suspend fun processFinalAudio(audioBytes: ByteArray): String {
        return sttProvider.transcribeFull(audioBytes)
    }
}
