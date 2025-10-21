package com.yourcompany.myagenticbrowser.ai.puter

import android.webkit.WebView
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * PuterSearchOrchestrator - Orchestrates search queries through Perplexity Sonar models via Puter.js infrastructure
 * This implementation follows the Perplexity Sonar model intercommunication system:
 * 1. The main AI chat model formulates specific questions in natural language to the Perplexity Sonar model
 * 2. Perplexity Sonar model responds in natural language with search results
 * 3. The main AI model processes these natural language responses as web search results
 * 4. The entire interaction feels like a conversation between two AI models
 * 
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class PuterSearchOrchestrator(private val puterClient: PuterClient) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    companion object {
        // Perplexity Sonar models available through Puter.js
        const val MODEL_SONAR_REASONING_PRO = "openrouter:perplexity/sonar-reasoning-pro"
        const val MODEL_SONAR_PRO = "openrouter:perplexity/sonar-pro"
        const val MODEL_SONAR_DEEP_RESEARCH = "openrouter:perplexity/sonar-deep-research"
        const val MODEL_SONAR_REASONING = "openrouter:perplexity/sonar-reasoning"
        const val MODEL_SONAR = "openrouter:perplexity/sonar"
    }
    
    /**
     * Perform a web search using natural language queries through Perplexity Sonar models
     * This method orchestrates the conversation between the main chat model and Perplexity Sonar model
     * 
     * @param query The natural language search query
     * @param webView Optional WebView for context-aware searches
     * @param model The Perplexity Sonar model to use (defaults to sonar-pro)
     * @return Search results as natural language response
     */
    suspend fun search(
        query: String,
        webView: WebView? = null,
        model: String = MODEL_SONAR_PRO
    ): String {
        Logger.logInfo("PuterSearchOrchestrator", "Performing web search through Puter.js infrastructure with Perplexity Sonar model: $model. Query: $query. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        return try {
            // Get context from WebView if available
            val context = webView?.let { extractWebViewContext(it) } ?: ""
            
            // Formulate the search query for the Perplexity Sonar model
            val searchPrompt = buildSearchPrompt(query, context)
            
            // Send the search query to the Perplexity Sonar model through Puter.js
            val searchResponse = puterClient.chat(
                webView = null, // We're not using WebView for the search itself
                message = searchPrompt,
                context = null, // Context is embedded in the prompt
                model = model
            )
            
            Logger.logInfo("PuterSearchOrchestrator", "Web search completed through Puter.js infrastructure. Response: $searchResponse")
            searchResponse
        } catch (e: Exception) {
            Logger.logError("PuterSearchOrchestrator", "Error performing web search through Puter.js infrastructure: ${e.message}", e)
            throw e
        }
    }
    
    /**
     * Perform a web search using the PuterClient search method through Puter.js infrastructure
     * This method directly uses the Perplexity Sonar models available through Puter.js
     * 
     * @param query The natural language search query
     * @param model The Perplexity Sonar model to use (defaults to sonar-pro)
     * @return Search results as natural language response
     */
    suspend fun searchWithPerplexitySonar(
        query: String,
        model: String = MODEL_SONAR_PRO
    ): String {
        Logger.logInfo("PuterSearchOrchestrator", "Performing web search with Perplexity Sonar through Puter.js infrastructure with model: $model. Query: $query. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        return try {
            // Send the search query to the Perplexity Sonar model through Puter.js
            val searchResponse = puterClient.search(
                query = query,
                model = model
            )
            
            Logger.logInfo("PuterSearchOrchestrator", "Web search with Perplexity Sonar completed through Puter.js infrastructure. Response: $searchResponse")
            searchResponse
        } catch (e: Exception) {
            Logger.logError("PuterSearchOrchestrator", "Error performing web search with Perplexity Sonar through Puter.js infrastructure: ${e.message}", e)
            throw e
        }
    }
    
    /**
     * Extract context from WebView for more informed searches
     */
    private fun extractWebViewContext(webView: WebView): String {
        return try {
            // In a real implementation, we would extract relevant context from the WebView
            // For now, we'll return a placeholder
            "Current page context from WebView"
        } catch (e: Exception) {
            Logger.logError("PuterSearchOrchestrator", "Error extracting WebView context through Puter.js infrastructure: ${e.message}", e)
            ""
        }
    }
    
    /**
     * Build a search prompt for the Perplexity Sonar model
     * This follows the natural language conversation approach between AI models
     */
    private fun buildSearchPrompt(query: String, context: String): String {
        return """
            You are Perplexity Sonar, an advanced web search AI. Your task is to search the web and provide comprehensive, accurate information in natural language.
            
            User Query: $query
            
            ${if (context.isNotEmpty()) "Context: $context" else ""}
            
            Please provide a detailed, natural language response that answers the user's query. Include relevant sources and citations where appropriate.
            
            Format your response as follows:
            1. Summary of findings
            2. Detailed information with supporting evidence
            3. Relevant sources and citations
            4. Additional context or related information
            
            Remember to only provide factual information that can be verified through web search.
        """.trimIndent()
    }
    
    /**
     * Perform a multi-turn search conversation
     * This allows for follow-up questions and deeper exploration of topics
     */
    suspend fun multiTurnSearch(
        conversation: List<SearchTurn>,
        model: String = MODEL_SONAR_PRO
    ): List<SearchTurn> {
        Logger.logInfo("PuterSearchOrchestrator", "Performing multi-turn search conversation through Puter.js infrastructure with Perplexity Sonar model: $model")
        
        return try {
            val updatedConversation = conversation.toMutableList()
            
            // Build conversation history for context
            val conversationHistory = buildConversationHistory(conversation)
            
            // Send the conversation to the Perplexity Sonar model
            val response = puterClient.chat(
                webView = null,
                message = conversationHistory,
                context = null,
                model = model
            )
            
            // Add the AI's response to the conversation
            updatedConversation.add(
                SearchTurn(
                    role = "assistant",
                    content = response,
                    timestamp = System.currentTimeMillis()
                )
            )
            
            Logger.logInfo("PuterSearchOrchestrator", "Multi-turn search conversation completed through Puter.js infrastructure")
            updatedConversation
        } catch (e: Exception) {
            Logger.logError("PuterSearchOrchestrator", "Error performing multi-turn search through Puter.js infrastructure: ${e.message}", e)
            throw e
        }
    }
    
    /**
     * Build conversation history for multi-turn searches
     */
    private fun buildConversationHistory(conversation: List<SearchTurn>): String {
        val history = StringBuilder()
        history.append("You are Perplexity Sonar, an advanced web search AI engaged in a conversation.\n\n")
        
        for (turn in conversation) {
            when (turn.role) {
                "user" -> history.append("User: ${turn.content}\n")
                "assistant" -> history.append("Assistant: ${turn.content}\n")
            }
        }
        
        history.append("\nPlease continue the conversation with a helpful response.")
        return history.toString()
    }
    
    /**
     * Cancel any ongoing search operations
     */
    fun cancel() {
        scope.cancel()
        Logger.logInfo("PuterSearchOrchestrator", "Cancelled search operations through Puter.js infrastructure")
    }
    
    /**
     * Data class representing a turn in a search conversation
     */
    @Serializable
    data class SearchTurn(
        val role: String, // "user" or "assistant"
        val content: String,
        val timestamp: Long = System.currentTimeMillis()
    )
    
    /**
     * Data class representing search results
     */
    @Serializable
    data class SearchResults(
        val query: String,
        val results: String,
        val sources: List<SearchSource> = emptyList(),
        val timestamp: Long = System.currentTimeMillis()
    )
    
    /**
     * Data class representing a search source
     */
    @Serializable
    data class SearchSource(
        val title: String,
        val url: String,
        val snippet: String
    )
}