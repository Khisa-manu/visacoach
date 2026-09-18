package com.visacoach.data.audio

import android.media.MediaPlayer
import android.util.Base64
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

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
