package com.yourcompany.myagenticbrowser

import android.app.Application
import com.yourcompany.myagenticbrowser.utilities.Logger
import com.yourcompany.myagenticbrowser.utilities.MemoryManager

/**
 * Application class for MyAgenticBrowser powered by Puter.js infrastructure
 * Initializes application-wide components and services through Puter.js infrastructure
 * All AI capabilities route through Puter.js as required
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.logInfo("MyApplication", "MyAgenticBrowser application created with Puter.js infrastructure")
        MemoryManager.logMemoryUsage()
        
        // Initialize PuterConfigManager
        com.yourcompany.myagenticbrowser.ai.puter.PuterConfigManager.initialize(this)
        
        // Initialize TTS manager for voice feedback
        com.yourcompany.myagenticbrowser.ai.puter.voice.TTSManager.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        // Shutdown TTS manager when application is terminated
        com.yourcompany.myagenticbrowser.ai.puter.voice.TTSManager.shutdown()
    }
}