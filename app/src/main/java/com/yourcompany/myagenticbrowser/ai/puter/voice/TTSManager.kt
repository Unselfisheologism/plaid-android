package com.yourcompany.myagenticbrowser.ai.puter.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import com.yourcompany.myagenticbrowser.utilities.Logger
import java.util.*

class TTSManager(context: Context) {
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                Logger.logInfo("TTSManager", "TextToSpeech initialized successfully")
            } else {
                Logger.logError("TSManager", "Failed to initialize TextToSpeech")
            }
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTSManager")
            Logger.logInfo("TTSManager", "Speaking: $text")
        } else {
            Logger.logError("TSManager", "TextToSpeech not initialized")
        }
    }

    fun stop() {
        textToSpeech?.stop()
        Logger.logInfo("TTSManager", "TTS stopped")
    }

    fun shutdown() {
        textToSpeech?.shutdown()
        Logger.logInfo("TTSManager", "TS shutdown")
    }

    fun setLanguage(locale: Locale): Int {
        return textToSpeech?.setLanguage(locale) ?: TextToSpeech.LANG_NOT_SUPPORTED
    }

    fun setSpeechRate(rate: Float) {
        textToSpeech?.setSpeechRate(rate)
    }

    fun setPitch(pitch: Float) {
        textToSpeech?.setPitch(pitch)
    }

    companion object {
        private var instance: TTSManager? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = TTSManager(context)
            }
        }

        fun getInstance(): TTSManager? {
            return instance
        }

        fun shutdown() {
            instance?.shutdown()
            instance = null
        }

        fun speak(text: String) {
            instance?.speak(text)
        }
    }
}