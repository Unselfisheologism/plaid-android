package com.yourcompany.myagenticbrowser.ai.puter.model

import android.webkit.WebView
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Model class representing the chat model in the Puter.js integration
 * This implements the requirement for clear separation between "chat model" and "search model" contexts
 * Part of the Perplexity Sonar model intercommunication system where models communicate using natural language only
 * All AI capabilities route through Puter.js infrastructure as required, no direct API keys for other providers
 */
class ChatModel(private val puterClient: PuterClient) {
    
    /**
     * Process a user message using the chat model through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required, no direct API keys for other providers
     */
    suspend fun processMessage(message: String, context: String? = null, webView: WebView? = null): String = withContext(Dispatchers.IO) {
        Logger.logInfo("ChatModel", "Processing message: $message through Puter.js infrastructure")
        
        try {
            val response = if (webView != null) {
                puterClient.chat(webView, message, context)
            } else {
                // Fallback if no WebView is available - this should throw an exception as WebView is required for authentication
                throw IllegalStateException("WebView is required for Puter.js authentication")
            }
            Logger.logInfo("ChatModel", "Received response from Puter.js infrastructure")
            response
        } catch (e: Exception) {
            Logger.logError("ChatModel", "Error processing message through Puter.js: ${e.message}", e)
            "I'm sorry, I encountered an error processing your request through Puter.js infrastructure: ${e.message}"
        }
    }
    
    /**
     * Process a search result from the search model through Puter.js infrastructure
     * This implements the requirement for the chat model to process natural language responses as web search results
     * All AI capabilities route through Puter.js as required, no direct API keys for other providers
     */
    suspend fun processSearchResult(searchQuery: String, searchResult: String, webView: WebView? = null): String = withContext(Dispatchers.IO) {
        Logger.logInfo("ChatModel", "Processing search result for query: $searchQuery through Puter.js infrastructure")
        
        try {
            val prompt = """
                As a research assistant powered by Puter.js, I received the following search results for the query "$searchQuery":
                $searchResult
                
                Please synthesize this information into a clear, concise answer to the original query using Puter.js capabilities.
                Include relevant sources or citations where possible and highlight the most important information.
            """.trimIndent()
            
            val response = if (webView != null) {
                puterClient.chat(webView, prompt)
            } else {
                // WebView is required for authentication
                throw IllegalStateException("WebView is required for Puter.js authentication")
            }
            Logger.logInfo("ChatModel", "Synthesized search results into response through Puter.js infrastructure")
            response
        } catch (e: Exception) {
            Logger.logError("ChatModel", "Error processing search result through Puter.js: ${e.message}", e)
            "I'm sorry, I encountered an error processing the search results through Puter.js infrastructure: ${e.message}"
        }
    }
}