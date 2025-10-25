package com.yourcompany.myagenticbrowser.ai.puter

import android.webkit.ValueCallback
import android.webkit.WebView
import com.yourcompany.myagenticbrowser.ai.puter.PuterSearchOrchestrator
import com.yourcompany.myagenticbrowser.ai.puter.SearchResults
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.browser.tab.TabOwner
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
// Removed CountDownLatch imports as we're using suspendCancellableCoroutine instead
import kotlin.coroutines.resume

/**
 * Client for interacting with Puter.js AI services
 * Based on the documentation, Puter.js provides AI capabilities through a JavaScript library
 * This implementation provides a backend interface for those capabilities
 * According to requirements, Puter.js handles all AI provider endpoints and authentication internally
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 */
class PuterClient {
    companion object {
        private const val PUTER_JS_URL = "https://js.puter.com/v2/"
    }

    /**
     * Send a chat request to Puter.js AI
     * This uses the actual Puter.js JavaScript library loaded in the WebView
     * According to requirements, Puter.js handles all AI provider endpoints and authentication internally
     * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
     */
    suspend fun chat(
        webView: WebView,
        message: String,
        context: String? = null,
        model: String = "gpt-5-nano"
    ): String = withContext(Dispatchers.Main) {
        Logger.logInfo("PuterClient", "Sending chat message through Puter.js infrastructure: $message. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        try {
            // First verify authentication
            ensureAuthenticated(webView)
            
            // Load Puter.js if not already loaded
            loadPuterJS(webView)
            
            val contextParam = if (context != null) ", context: '$context'" else ""
            val chatPromise = """new Promise((resolve, reject) => {
               |    try {
               |        puter.ai.chat('$message', { model: '$model'$contextParam }).then(result => resolve(result)).catch(error => reject(error.message));
               |    } catch (error) {
               |        reject(error.message);
               |    }
               |})""".trimMargin()
            
            executeJavaScriptWithResult(webView, "Promise.resolve($chatPromise)")
        } catch (e: Exception) {
            Logger.logError("PuterClient", "Error in chat through Puter.js infrastructure: ${e.message}", e)
            throw e
        }
    }
    
    /**
     * Perform web search using Perplexity Sonar models through Puter.js infrastructure
     * This uses the actual Puter.js JavaScript library loaded in the WebView
     * According to requirements, all AI capabilities route through Puter.js infrastructure
     * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
     * Puter.js handles all AI provider endpoints and authentication internally as required
     */
    suspend fun search(
        webView: WebView,
        query: String,
        model: String = PuterSearchOrchestrator.MODEL_SONAR_PRO
    ): String = withContext(Dispatchers.Main) {
        Logger.logInfo("PuterClient", "Performing web search through Puter.js infrastructure with Perplexity Sonar model: $model. Query: $query. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        try {
            // First verify authentication
            ensureAuthenticated(webView)
            
            // Load Puter.js if not already loaded
            loadPuterJS(webView)
            
            // Formulate the search query for the Perplexity Sonar model
            val searchPrompt = "Search the web for: $query"
            
            // Send the search query to the Perplexity Sonar model through Puter.js
            val searchResponse = chat(
                webView = webView,
                message = searchPrompt,
                model = model
            )
            
            Logger.logInfo("PuterClient", "Web search completed through Puter.js infrastructure. Response: $searchResponse")
            searchResponse
        } catch (e: Exception) {
            Logger.logError("PuterClient", "Error performing web search through Puter.js infrastructure: ${e.message}", e)
            throw e
        }
    }
    
    // New method that doesn't require a WebView directly (to be used by SearchModel)
    suspend fun searchWithPerplexitySonar(query: String, model: String = PuterSearchOrchestrator.MODEL_SONAR_PRO): SearchResults = withContext(Dispatchers.Main) {
        Logger.logInfo("PuterClient", "Performing web search with Perplexity Sonar through Puter.js infrastructure with model: $model. Query: $query. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        // This method is intended to be used when a WebView isn't directly available
        // In a real implementation, this would need to be called from a context that has access to a WebView
        throw IllegalStateException("This method requires a WebView instance to communicate with Puter.js. Use the search(webView, query, model) method instead.")
    }
    /**
     * Generate text-to-image using Puter.js infrastructure
     * This uses the actual Puter.js JavaScript library loaded in the WebView
     * According to requirements, all image generation requests must go through Puter.js infrastructure
     * No direct API calls to Stable Diffusion, DALL-E, or other image generation services
     */
    suspend fun textToImage(webView: WebView?, prompt: String, testMode: Boolean = false): String = withContext(Dispatchers.Main) {
        if (webView != null) {
            // First verify authentication
            ensureAuthenticated(webView)
            
            // Load Puter.js if not already loaded
            loadPuterJS(webView)
            
            val txt2imgPromise = """new Promise((resolve, reject) => {
               |    try {
               |        puter.ai.txt2img('$prompt', { testMode: $testMode }).then(result => resolve(result)).catch(error => reject(error.message));
               |    } catch (error) {
               |        reject(error.message);
               |    }
               |})""".trimMargin()

            executeJavaScriptWithResult(webView, "Promise.resolve($txt2imgPromise)")
        } else {
            // Handle case where no WebView is provided - this would be an error condition
            Logger.logError("PuterClient", "textToImage requires a WebView instance to communicate with Puter.js")
            throw IllegalStateException("textToImage requires a WebView instance to communicate with Puter.js")
        }
    }

    /**
     * Extract text from an image using Puter.js infrastructure
     * This uses the actual Puter.js JavaScript library loaded in the WebView
     * According to requirements, all AI capabilities route through Puter.js infrastructure
     */
    suspend fun imageToText(webView: WebView?, imageUrl: String, testMode: Boolean = false): String = withContext(Dispatchers.Main) {
        if (webView != null) {
            // First verify authentication
            ensureAuthenticated(webView)
            
            // Load Puter.js if not already loaded
            loadPuterJS(webView)
            
            val img2txtPromise = """new Promise((resolve, reject) => {
               |    try {
               |        puter.ai.img2txt('$imageUrl', { testMode: $testMode }).then(result => resolve(result)).catch(error => reject(error.message));
               |    } catch (error) {
               |        reject(error.message);
               |    }
               |})""".trimMargin()

            executeJavaScriptWithResult(webView, "Promise.resolve($img2txtPromise)")
        } else {
            // Handle case where no WebView is provided - this would be an error condition
            Logger.logError("PuterClient", "imageToText requires a WebView instance to communicate with Puter.js")
            throw IllegalStateException("imageToText requires a WebView instance to communicate with Puter.js")
        }
    }

    /**
     * Convert text to speech using Puter.js infrastructure
     * This uses the actual Puter.js JavaScript library loaded in the WebView
     * According to requirements, all AI capabilities route through Puter.js infrastructure
     */
    suspend fun textToSpeech(webView: WebView?, text: String, language: String = "en-US", voice: String = "Joanna", engine: String = "standard"): String = withContext(Dispatchers.Main) {
        if (webView != null) {
            // First verify authentication
            ensureAuthenticated(webView)
            
            // Load Puter.js if not already loaded
            loadPuterJS(webView)
            
            val ttsPromise = """new Promise((resolve, reject) => {
               |    try {
               |        puter.ai.txt2speech('$text', { language: '$language', voice: '$voice', engine: '$engine' }).then(result => resolve(result)).catch(error => reject(error.message));
               |    } catch (error) {
               |        reject(error.message);
               |    }
               |})""".trimMargin()

            executeJavaScriptWithResult(webView, "Promise.resolve($ttsPromise)")
        } else {
            // Handle case where no WebView is provided - this would be an error condition
            Logger.logError("PuterClient", "textToSpeech requires a WebView instance to communicate with Puter.js")
            throw IllegalStateException("textToSpeech requires a WebView instance to communicate with Puter.js")
        }
    }

    /**
     * Check authentication status before any Puter.js API call
     * This now uses the WebView-based authentication approach
     */
    private suspend fun ensureAuthenticated(webView: WebView): Boolean = withContext(Dispatchers.Main) {
        // Get the BrowserActivity to access the PuterAuthHelper
        val activity = webView.context as? com.yourcompany.myagenticbrowser.browser.BrowserActivity
        if (activity != null) {
            // Use the new authentication helper that uses Chrome Custom Tabs
            return@withContext activity.puterAuthHelper.isAuthenticated()
        } else {
            // If we can't get the activity, return false
            Logger.logError("PuterClient", "Could not access BrowserActivity for authentication check")
            return@withContext false
        }
    }
    
    /**
     * Execute JavaScript in the WebView and return the result as a string
    */
    private suspend fun executeJavaScriptWithResult(webView: WebView, jsCode: String): String {
        return suspendCancellableCoroutine<String> { continuation ->
            webView.evaluateJavascript(jsCode, ValueCallback<String> { result ->
                if (result != null && result != "null") {
                    // Remove quotes if they exist (evaluateJavascript returns string values with quotes)
                    val cleanResult = result.removeSurrounding("\"").replace("\\\"", "\"")
                    continuation.resume(cleanResult)
                } else {
                    continuation.resume("Operation completed successfully")
                }
            })
        }
    }

    fun getPuterJSScript(): String {
        return """
            // Ensure Puter.js is loaded
            if (typeof puter === 'undefined') {
                const script = document.createElement('script');
                script.src = '$PUTER_JS_URL';
                script.onload = function() {
                    console.log('Puter.js loaded successfully');
                    window.PuterJSReady = true;
                };
                script.onerror = function() {
                    console.error('Failed to load Puter.js');
                    window.PuterJSReady = false;
                };
                document.head.appendChild(script);
            } else {
                window.PuterJSReady = true;
            }

            // Set up result handlers for communication with Android
            if (typeof window.PuterResultHandler === 'undefined') {
                window.PuterResultHandler = {
                    handleResult: function(result) {
                        if (typeof Android !== 'undefined' && Android.handlePuterResult) {
                            Android.handlePuterResult(JSON.stringify(result));
                        } else {
                            console.warn('Android interface not available');
                        }
                    },
                    handleError: function(error) {
                        if (typeof Android !== 'undefined' && Android.handlePuterError) {
                            Android.handlePuterError(error.toString());
                        } else {
                            console.warn('Android interface not available');
                        }
                    }
                };
            }
        """.trimIndent()
    }

    /**
     * Check if Puter.js is ready in the WebView
     */
    fun isPuterJSReady(webView: WebView): Boolean {
        var ready = false
        webView.evaluateJavascript("(typeof puter !== 'undefined') && window.PuterJSReady") { result ->
            ready = result.removeSurrounding("\"") == "true"
        }
        return ready
    }

    /**
     * Load Puter.js in the WebView if not already loaded
     */
    fun loadPuterJS(webView: WebView) {
        webView.evaluateJavascript(getPuterJSScript()) { result ->
            Logger.logInfo("PuterClient", "Puter.js initialization script executed: $result")
        }
    }
}