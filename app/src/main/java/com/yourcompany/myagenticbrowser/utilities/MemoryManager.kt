package com.yourcompany.myagenticbrowser.utilities

import android.util.Log
import java.lang.ref.WeakReference

object MemoryManager {
    private const val TAG = "MemoryManager"
    
    fun logMemoryUsage() {
        val runtime = Runtime.getRuntime()
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val usedMemory = totalMemory - freeMemory
        
        Logger.logInfo(TAG, "Memory - Used: ${usedMemory / 1024 / 1024} MB, Free: ${freeMemory / 1024 / 1024} MB, Total: ${totalMemory / 1024 / 1024} MB")
    }
    
    fun gc() {
        System.gc()
        logMemoryUsage()
    }
}