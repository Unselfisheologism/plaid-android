package com.yourcompany.myagenticbrowser.utilities

import android.util.Log

/**
 * Simple logging utility class
 */
object Logger {
    private const val TAG = "MyAgenticBrowser"

    fun logInfo(tag: String, message: String) {
        Log.i(TAG, "[$tag] $message")
    }

    fun logError(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(TAG, "[$tag] $message", throwable)
        } else {
            Log.e(TAG, "[$tag] $message")
        }
    }

    fun logDebug(tag: String, message: String) {
        Log.d(TAG, "[$tag] $message")
    }

    fun logWarning(tag: String, message: String) {
        Log.w(TAG, "[$tag] $message")
    }
}