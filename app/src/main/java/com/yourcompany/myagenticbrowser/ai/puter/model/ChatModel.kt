package com.yourcompany.myagenticbrowser.ai.puter.model

import android.webkit.WebView
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.utilities.Logger

class ChatModel(private val puterClient: PuterClient) {
    
    private var webView: android.webkit.WebView? = null
    
    fun setWebView(webView: android.webkit.WebView?) {
        this.webView = webView
    }
    
    suspend fun sendMessage(message: String, context: String? = null): String {
        Logger.logInfo("ChatModel", "Sending message to Puter.js: $message")
        // This would use the puterClient to send the message to Puter.js
        return if (webView != null) {
            puterClient.chat(
                webView = webView,
                message = message,
                context = context
            )
        } else {
            Logger.logError("ChatModel", "No WebView available for chat operation")
            throw IllegalStateException("Chat operations require a WebView instance to communicate with Puter.js")
        }
    }
}