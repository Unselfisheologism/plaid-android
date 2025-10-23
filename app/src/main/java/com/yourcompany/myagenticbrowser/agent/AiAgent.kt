package com.yourcompany.myagenticbrowser.agent

import android.webkit.WebView
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.PuterSearchOrchestrator
import com.yourcompany.myagenticbrowser.ai.puter.model.ChatModel
import com.yourcompany.myagenticbrowser.ai.puter.model.SearchModel
import com.yourcompany.myagenticbrowser.ai.puter.SearchResults
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * AI Agent that routes all AI capabilities through Puter.js infrastructure
 * Implements the requirement that Puter.js is the sole AI infrastructure provider
 * Replacing all traditional AI API providers like OpenAI, Anthropic, Google, etc.
 * All AI features must route through Puter.js, no direct API keys for other providers
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class AiAgent private constructor(
     val context: AgentContext = AgentContext(),
     private val puterClient: PuterClient,
     private val chatModel: ChatModel,
     private val searchModel: SearchModel,
     private val searchOrchestrator: PuterSearchOrchestrator
 ) {
    data class AgentContext(
        val activeTabUrl: String = "",
        val activeTabTitle: String = "",
        val cookies: Map<String, String> = emptyMap(),
        val pageContent: String = "",
        val webView: WebView? = null
    ) : android.os.Parcelable {
        constructor(parcel: android.os.Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readHashMap(String::class.java.classLoader) as? Map<String, String> ?: emptyMap(),
            parcel.readString() ?: "",
            null // WebView is not parcelable, so we can't restore it
        )
 
       override fun describeContents(): Int = 0

        override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
            dest.writeString(activeTabUrl)
            dest.writeString(activeTabTitle)
            dest.writeMap(cookies as? Map<*, *>)
            dest.writeString(pageContent)
            // WebView is not parcelable, so we don't write it
        }

        companion object CREATOR : android.os.Parcelable.Creator<AgentContext> {
            override fun createFromParcel(parcel: android.os.Parcel): AgentContext = AgentContext(parcel)
            override fun newArray(size: Int): Array<AgentContext?> = arrayOfNulls(size)
        }
    }
    
    sealed class AgentResponse {
        data class Success(val message: String) : AgentResponse()
        data class Error(val message: String) : AgentResponse()
        data class RequiresAutomation(val message: String, val task: String, val details: Map<String, String>? = null) : AgentResponse()
        data class RequiresSearch(val query: String, val results: String) : AgentResponse()
        data class ImageGenerated(val imageUrl: String) : AgentResponse()
    }
    
    /**
     * Process a command using Puter.js AI through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required, no direct API keys for other providers
     * Puter.js handles all AI provider endpoints and authentication internally as required
     */
    suspend fun processCommand(command: String): AgentResponse = withContext(Dispatchers.IO) {
        Logger.logInfo("AiAgent", "Processing command: $command through Puter.js infrastructure")
        
        try {
            // Check if we have Puter.js credentials configured through Puter.js infrastructure
            // For now, we'll assume credentials are available since Puter.js handles authentication internally
            // In a real implementation, we might check if Puter.js is properly loaded and authenticated
            val hasCredentials = true // Simplified for now since Puter.js handles auth internally
            if (!hasCredentials) {
                return@withContext AgentResponse.Error("Puter.js configuration not set up. Please configure through the AI Configuration menu. All AI capabilities must route through Puter.js infrastructure as required. Puter.js handles all AI provider endpoints and authentication internally.")
            }
            
            // Ensure we have a WebView to work with
            val webView = context.webView
            if (webView == null) {
                return@withContext AgentResponse.Error("No WebView available for Puter.js operations. All AI capabilities must route through Puter.js infrastructure as required.")
            }
            
            // Determine the type of command and route appropriately through Puter.js infrastructure
            return@withContext when {
                command.contains("search", ignoreCase = true) ||
                command.contains("find", ignoreCase = true) ||
                command.contains("what is", ignoreCase = true) ||
                command.contains("who is", ignoreCase = true) ||
                command.contains("when", ignoreCase = true) -> {
                    
                    // Use the search orchestrator for natural language search through Puter.js infrastructure
                    // This implements the Perplexity Sonar model intercommunication system requirement
                    // where the main AI chat model communicates with search models using natural language only
                    val searchResults = searchOrchestrator.searchWithPerplexitySonar(
                        query = command,
                        model = PuterSearchOrchestrator.MODEL_SONAR_PRO
                    )
                    
                    // Safely access the error property
                    val error = searchResults.error
                    if (error != null) {
                        AgentResponse.Error(error)
                    } else {
                        val formattedResults = formatSearchResults(searchResults)
                        AgentResponse.RequiresSearch(command, formattedResults)
                    }
                }
                
                command.contains("create", ignoreCase = true) ||
                command.contains("notion", ignoreCase = true) ||
                command.contains("gmail", ignoreCase = true) -> {
                    // Parse the command to extract specific parameters for automation
                    val (task, details) = parseAutomationCommand(command)
                    
                    // For now, still return automation request for these actions through Puter.js infrastructure
                    // In a real implementation, we might use Puter.js to generate the specific steps through Puter.js infrastructure
                    // This would leverage session cookies from browser for API-speed access to services through Puter.js infrastructure
                    AgentResponse.RequiresAutomation(
                        message = "Processing request to external service using your authenticated session through Puter.js infrastructure...",
                        task = task,
                        details = details
                    )
                }
                
                command.contains("image", ignoreCase = true) ||
                command.contains("picture", ignoreCase = true) ||
                command.contains("photo", ignoreCase = true) ||
                command.contains("generate", ignoreCase = true) -> {
                    // Use Puter.js for image generation through Puter.js infrastructure
                    // All image generation requests must go through Puter.js infrastructure as required
                    // No direct API calls to Stable Diffusion, DALL-E, or other image generation services
                    try {
                        val imageUrl = puterClient.textToImage(webView, command)
                        AgentResponse.ImageGenerated(imageUrl)
                    } catch (e: Exception) {
                        Logger.logError("AiAgent", "Error generating image through Puter.js infrastructure: ${e.message}", e)
                        AgentResponse.Error("Error generating image through Puter.js infrastructure: ${e.message}")
                    }
                }
                
                else -> {
                    // Use Puter.js for general chat through Puter.js infrastructure
                    // All AI features route through Puter.js as required, no direct API keys for other providers
                    // Puter.js handles all AI provider endpoints and authentication internally as required
                    try {
                        val aiResponse = puterClient.chat(
                            webView = webView,
                            message = command,
                            context = "Current context: ${context.activeTabTitle} at ${context.activeTabUrl}. Page content: ${context.pageContent.take(500)}..."
                        )
                        AgentResponse.Success(aiResponse)
                    } catch (e: Exception) {
                        Logger.logError("AiAgent", "Error in chat through Puter.js infrastructure: ${e.message}", e)
                        AgentResponse.Error("Error in chat through Puter.js infrastructure: ${e.message}")
                    }
                }
            }
        } catch (e: Exception) {
            Logger.logError("AiAgent", "Error processing command through Puter.js infrastructure: ${e.message}", e)
            AgentResponse.Error("Error processing command through Puter.js infrastructure: ${e.message}")
        }
    }

    companion object {
        /**
         * Factory method to create an AiAgent with Puter.js integration through Puter.js infrastructure
         * Ensures that all AI capabilities route through Puter.js as required
         * Replaces all traditional AI API providers with Puter.js infrastructure
         * All AI features must route through Puter.js, no direct API keys for other providers
         * Puter.js handles all AI provider endpoints and authentication internally as required
         */
        fun create(context: AgentContext = AgentContext()): AiAgent {
            val puterClient = PuterClient() // Using the default constructor
            val chatModel = com.yourcompany.myagenticbrowser.ai.puter.model.ChatModel(puterClient)
            val searchModel = com.yourcompany.myagenticbrowser.ai.puter.model.SearchModel(puterClient)
            val searchOrchestrator = PuterSearchOrchestrator(puterClient, chatModel, searchModel)
            
            return AiAgent(
                context = context,
                puterClient = puterClient,
                chatModel = chatModel,
                searchModel = searchModel,
                searchOrchestrator = searchOrchestrator
            )
        }
    }
    
    /**
     * Format search results for display
     */
    private fun formatSearchResults(results: PuterSearchOrchestrator.SearchResults): String {
        if (results.results.isEmpty()) {
            return "No search results found."
        }
        
        // The results string from Puter.js contains the formatted search results
        // We can return it as is or add some additional formatting
        return "Search Results:\n\n${results.results}"
    }
    
    /**
     * Determine the appropriate task based on the command
     */
    private fun determineTaskFromCommand(command: String): String {
        return when {
            command.contains("create", ignoreCase = true) || command.contains("notion", ignoreCase = true) -> "create_notion_page"
            command.contains("email", ignoreCase = true) || command.contains("gmail", ignoreCase = true) -> "gmail_action"
            command.contains("calendar", ignoreCase = true) -> "calendar_action"
            else -> "generic_task"
        }
    }

    /**
     * Parse an automation command to extract the task type and specific details
     */
    private fun parseAutomationCommand(command: String): Pair<String, Map<String, String>?> {
        val lowerCommand = command.lowercase()
        
        return when {
            lowerCommand.contains("notion") || lowerCommand.contains("create") -> {
                // Extract title and content for Notion page creation
                val title = extractParameter(command, listOf("title", "named", "called"))
                val content = extractParameter(command, listOf("content", "with content", "containing"))
                
                val details = mutableMapOf<String, String>().apply {
                    if (!title.isNullOrBlank()) put("title", title)
                    if (!content.isNullOrBlank()) put("content", content)
                }
                
                Pair("create_notion_page", if (details.isNotEmpty()) details else null)
            }
            
            lowerCommand.contains("gmail") || lowerCommand.contains("email") || lowerCommand.contains("compose") -> {
                // Extract email parameters
                val to = extractParameter(command, listOf("to", "recipient"))
                val subject = extractParameter(command, listOf("subject", "title"))
                val body = extractParameter(command, listOf("body", "message", "content"))
                
                val details = mutableMapOf<String, String>().apply {
                    if (!to.isNullOrBlank()) put("to", to)
                    if (!subject.isNullOrBlank()) put("subject", subject)
                    if (!body.isNullOrBlank()) put("body", body)
                    put("action", "compose")
                }
                
                Pair("gmail_action", if (details.isNotEmpty()) details else null)
            }
            
            else -> {
                // For generic tasks, just return the task type
                Pair(determineTaskFromCommand(command), null)
            }
        }
    }

    /**
     * Extract a parameter value from a command string based on keywords
     */
    private fun extractParameter(command: String, keywords: List<String>): String? {
        val lowerCommand = command.lowercase()
        
        for (keyword in keywords) {
            val pattern = Regex("(?i)\\b$keyword\\b\\s+(.+?)(?:\\s+|$|and|with|for|that)")
            val match = pattern.find(command)
            if (match != null) {
                var value = match.groupValues[1].trim()
                // Remove any trailing punctuation
                value = value.replace(Regex("[.,;!?]+$"), "")
                return value
            }
            
            // Alternative pattern for "keyword: value" format
            val colonPattern = Regex("(?i)\\b$keyword\\s*[:=]\\s*(.+?)(?:\\s+|$|and|with|for|that)")
            val colonMatch = colonPattern.find(command)
            if (colonMatch != null) {
                var value = colonMatch.groupValues[1].trim()
                value = value.replace(Regex("[.,;!?]+$"), "")
                return value
            }
        }
        
        return null
    }
}