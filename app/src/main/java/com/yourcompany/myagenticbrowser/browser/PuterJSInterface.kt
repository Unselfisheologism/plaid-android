package com.yourcompany.myagenticbrowser.browser

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * JavaScript interface to allow communication between WebView JavaScript and Android through Puter.js infrastructure
 * This enables the Puter.js JavaScript library to communicate with our Android application
 * Facilitates the integration between Puter.js running in WebView and the Android application
 * All AI capabilities route through Puter.js as required
 */
class PuterJSInterface(
    private val puterClient: PuterClient,
    private val webView: WebView
) {
    private val scope = CoroutineScope(Dispatchers.Main)
    
    /**
     * Handle general results from Puter.js JavaScript through Puter.js infrastructure
     * This enables communication between Puter.js running in WebView and Android application
     * All AI capabilities route through Puter.js as required
     */
    @JavascriptInterface
    fun handlePuterResult(result: String) {
        Logger.logInfo("PuterJSInterface", "Received result from JS through Puter.js infrastructure: $result")
        // Handle the result in the Android application through Puter.js infrastructure
        // This could trigger UI updates or other actions through Puter.js infrastructure
    }
    
    /**
     * Handle errors from Puter.js JavaScript through Puter.js infrastructure
     * This enables communication between Puter.js running in WebView and Android application
     * All AI capabilities route through Puter.js as required
     */
    @JavascriptInterface
    fun handlePuterError(error: String) {
        Logger.logError("PuterJSInterface", "Received error from JS through Puter.js infrastructure: $error")
        // Handle the error in the Android application through Puter.js infrastructure
    }
    
    /**
     * Call Puter.js chat functionality from Android through Puter.js infrastructure
     * This enables communication between Android application and Puter.js running in WebView
     * All AI capabilities route through Puter.js as required
     */
    @JavascriptInterface
    fun callPuterChat(message: String, context: String?, model: String?) {
        scope.launch {
            try {
                val result = puterClient.chat(
                    webView = webView,
                    message = message,
                    context = context,
                    model = model ?: "gpt-5-nano"
                )
                handlePuterResult(result)
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error in callPuterChat through Puter.js infrastructure: ${e.message}", e)
                handlePuterError(e.message ?: "Unknown error through Puter.js infrastructure")
            }
        }
    }
    
    /**
     * Call Puter.js text-to-image functionality from Android through Puter.js infrastructure
     * This enables communication between Android application and Puter.js running in WebView
     * All image generation requests must go through Puter.js infrastructure as required
     */
    @JavascriptInterface
    fun callPuterTxt2Img(prompt: String, testMode: Boolean?) {
        scope.launch {
            try {
                val result = puterClient.textToImage(webView, prompt, testMode ?: false)
                handlePuterResult(result)
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error in callPuterTxt2Img through Puter.js infrastructure: ${e.message}", e)
                handlePuterError(e.message ?: "Unknown error through Puter.js infrastructure")
            }
        }
    }
    
    /**
     * Call Puter.js image-to-text functionality from Android through Puter.js infrastructure
     * This enables communication between Android application and Puter.js running in WebView
     * All AI capabilities route through Puter.js as required
     */
    @JavascriptInterface
    fun callPuterImg2Txt(imageUrl: String, testMode: Boolean?) {
        scope.launch {
            try {
                val result = puterClient.imageToText(webView, imageUrl, testMode ?: false)
                handlePuterResult(result)
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error in callPuterImg2Txt through Puter.js infrastructure: ${e.message}", e)
                handlePuterError(e.message ?: "Unknown error through Puter.js infrastructure")
            }
        }
    }
    
    /**
     * Call Puter.js text-to-speech functionality from Android through Puter.js infrastructure
     * This enables communication between Android application and Puter.js running in WebView
     * All AI capabilities route through Puter.js as required
     */
    @JavascriptInterface
    fun callPuterTxt2Speech(text: String, language: String?, voice: String?, engine: String?) {
        scope.launch {
            try {
                val result = puterClient.textToSpeech(
                    webView,
                    text,
                    language ?: "en-US",
                    voice ?: "Joanna",
                    engine ?: "standard"
                )
                handlePuterResult(result)
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error in callPuterTxt2Speech through Puter.js infrastructure: ${e.message}", e)
                handlePuterError(e.message ?: "Unknown error through Puter.js infrastructure")
            }
        }
    }
    
    /**
     * Initialize Puter.js in the WebView
     * This ensures Puter.js is properly loaded and configured for communication
     */
    @JavascriptInterface
    fun initializePuterJS() {
        scope.launch {
            puterClient.loadPuterJS(webView)
        }
    }
    
    /**
     * Launch Puter authentication using WebView-based approach
     * This is the correct implementation for Puter.js authentication
     */
    @JavascriptInterface
    fun launchPuterAuth() {
        scope.launch {
            try {
                val activity = webView.context as? BrowserActivity
                activity?.let { browserActivity ->
                    browserActivity.puterAuthHelper.launchAuthentication()
                }
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error launching Puter auth: ${e.message}", e)
            }
        }
    }
    
    /**
     * Check if user is authenticated with Puter
     * This uses the WebView-based authentication approach
     */
    @JavascriptInterface
    fun isPuterAuthenticated(): Boolean {
        return try {
            val activity = webView.context as? BrowserActivity
            activity?.puterAuthHelper?.isAuthenticated() ?: false
        } catch (e: Exception) {
            Logger.logError("PuterJSInterface", "Error checking Puter auth status: ${e.message}", e)
            false
        }
    }
    
    /**
     * Show chat popup from JavaScript
     * This method is called from the agent_home.html page
     */
    @JavascriptInterface
    fun showChatPopup() {
        scope.launch {
            try {
                val activity = webView.context as? BrowserActivity
                activity?.showChatPopup()
            } catch (e: Exception) {
                Logger.logError("PuterJSInterface", "Error showing chat popup: ${e.message}", e)
            }
        }
    }
}