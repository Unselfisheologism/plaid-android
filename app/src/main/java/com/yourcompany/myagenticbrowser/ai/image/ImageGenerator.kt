package com.yourcompany.myagenticbrowser.ai.image

import android.content.Context
import android.webkit.WebView
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.utilities.Logger

class ImageGenerator(private val puterClient: PuterClient) {
    
    private var webView: android.webkit.WebView? = null
    
    fun setWebView(webView: android.webkit.WebView?) {
        this.webView = webView
    }
    
    suspend fun textToImage(context: Context, prompt: String): String {
        Logger.logInfo("ImageGenerator", "Generating image from text: $prompt")
        return if (webView != null) {
            puterClient.textToImage(
                webView = webView,
                prompt = prompt
            )
        } else {
            // Fallback behavior - could be implemented differently based on requirements
            Logger.logError("ImageGenerator", "No WebView available for image generation")
            throw IllegalStateException("Image generation requires a WebView instance to communicate with Puter.js")
        }
    }
    
    suspend fun imageToText(context: Context, imageUrl: String): String {
        Logger.logInfo("ImageGenerator", "Extracting text from image: $imageUrl")
        return if (webView != null) {
            puterClient.imageToText(
                webView = webView,
                imageUrl = imageUrl
            )
        } else {
            // Fallback behavior - could be implemented differently based on requirements
            Logger.logError("ImageGenerator", "No WebView available for image extraction")
            throw IllegalStateException("Image extraction requires a WebView instance to communicate with Puter.js")
        }
    }
}