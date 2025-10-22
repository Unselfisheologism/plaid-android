package com.yourcompany.myagenticbrowser.utilities

import android.app.ActivityManager
import android.content.Context
import android.util.Log

object MemoryManager {
    private const val TAG = "MemoryManager"
    
    fun logMemoryUsage() {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        val totalMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        
        Log.i(TAG, "Memory Usage - Max: ${formatBytes(maxMemory)}, Total: ${formatBytes(totalMemory)}, Free: ${formatBytes(freeMemory)}")
    }
    
    private fun formatBytes(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB")
        var size = bytes.toDouble()
        var unitIndex = 0
        
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        
        return "${String.format("%.2f", size)} ${units[unitIndex]}"
    }
    
    fun getAvailableMemory(context: Context): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem
    }
    
    fun gc() {
        // Explicitly call garbage collection
        Runtime.getRuntime().gc()
    }
}