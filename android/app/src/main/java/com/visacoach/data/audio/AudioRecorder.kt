package com.visacoach.data.audio

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRecorder @Inject constructor() {
    private var isRecording = false
    private var recordingJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    fun startRecording(onAudioChunk: (String) -> Unit) {
        if (isRecording) return
        isRecording = true

        recordingJob = scope.launch {
            val sampleRate = 16000
            val channelConfig = AudioFormat.CHANNEL_IN_MONO
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat).coerceAtLeast(2048)

            try {
                val audioRecord = AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    sampleRate,
                    channelConfig,
                    audioFormat,
                    bufferSize
                )

                val buffer = ByteArray(bufferSize)
                audioRecord.startRecording()

                while (isActive && isRecording) {
                    val read = audioRecord.read(buffer, 0, buffer.size)
                    if (read > 0) {
                        val chunk = buffer.copyOf(read)
                        val b64 = Base64.encodeToString(chunk, Base64.NO_WRAP)
                        onAudioChunk(b64)
                    }
                }

                audioRecord.stop()
                audioRecord.release()
            } catch (e: SecurityException) {
                // Permission not yet granted
            } catch (e: Exception) {
                // Audio hardware initialization error
            }
        }
    }

    fun stopRecording() {
        isRecording = false
        recordingJob?.cancel()
        recordingJob = null
    }
}

@Singleton
class AudioPlayer @Inject constructor() {
    private var mediaPlayer: MediaPlayer? = null

    fun playBase64Audio(base64Audio: String, onCompletion: () -> Unit) {
        stop()
        try {
            val decoded = Base64.decode(base64Audio, Base64.DEFAULT)
            val tempFile = File.createTempFile("tts_audio_", ".wav")
            FileOutputStream(tempFile).use { it.write(decoded) }

            mediaPlayer = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                prepare()
                setOnCompletionListener {
                    tempFile.delete()
                    onCompletion()
                }
                start()
            }
        } catch (e: Exception) {
            onCompletion()
        }
    }

    fun stop() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (e: Exception) {
            // Ignored
        }
        mediaPlayer = null
    }
}
