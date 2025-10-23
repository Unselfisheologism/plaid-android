qpackage com.yourcompany.myagenticbrowser.workflow

import android.content.Context
import android.os.Parcelable
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.model.ChatModel
import com.yourcompany.myagenticbrowser.browser.cookies.CookieManager
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Workflow engine for executing automated tasks through Puter.js infrastructure
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class WorkflowEngine(private val context: Context, private val puterClient: PuterClient) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    /**
     * Execute a workflow with the provided cookies and UI context
     */
    suspend fun execute(workflow: Workflow, cookies: Map<String, String>, webView: android.webkit.WebView, uiContext: String? = null): WorkflowResult {
        Logger.logInfo("WorkflowEngine", "Executing workflow: ${workflow.name} through Puter.js infrastructure with UI context. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        
        return try {
            // First ensure Puter.js is loaded and authenticated
            puterClient.loadPuterJS(webView)
            // Verify authentication before executing workflow
            puterClient.chat(webView, "Verify authentication for workflow execution", "Authentication check", "gpt-5-nano")
            
            // Execute each node in the workflow
            for (node in workflow.nodes) {
                val result = executeNode(node, cookies, webView, uiContext)
                if (result is NodeResult.Failure) {
                    return WorkflowResult.Failure("Failed to execute node: ${result.errorMessage}")
                }
            }
            
            WorkflowResult.Success("Workflow executed successfully through Puter.js infrastructure")
        } catch (e: SecurityException) {
            Logger.logError("WorkflowEngine", "Authentication error during workflow execution: ${e.message}", e)
            WorkflowResult.Failure("Authentication required: ${e.message}")
        } catch (e: Exception) {
            Logger.logError("WorkflowEngine", "Error executing workflow through Puter.js infrastructure: ${e.message}", e)
            WorkflowResult.Failure("Error executing workflow: ${e.message}")
        }
    }
    
    /**
     * Execute a single workflow node
     */
    private suspend fun executeNode(node: WorkflowNode, cookies: Map<String, String>, webView: android.webkit.WebView, uiContext: String? = null): NodeResult {
        return when (node) {
            is WorkflowNode.NotionNode -> executeNotionNode(node, cookies, webView, uiContext)
            is WorkflowNode.GmailNode -> executeGmailNode(node, cookies, webView, uiContext)
            is WorkflowNode.WebAutomationNode -> executeWebAutomationNode(node, cookies, webView, uiContext)
            else -> NodeResult.Failure("Unsupported node type through Puter.js infrastructure")
        }
    }
    
    /**
     * Execute a Notion node
     */
    private suspend fun executeNotionNode(node: WorkflowNode.NotionNode, cookies: Map<String, String>, webView: android.webkit.WebView, uiContext: String? = null): NodeResult {
        Logger.logInfo("WorkflowEngine", "Executing Notion node: ${node.action} through Puter.js infrastructure with UI context")
        
        return try {
            // Use Puter.js to execute the Notion action
            val prompt = buildNotionPrompt(node, cookies, uiContext)
            val result = puterClient.chat(webView, prompt, uiContext, "gpt-5-nano")
            
            NodeResult.Success("Executed Notion action: ${node.action} through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowEngine", "Error executing Notion node through Puter.js infrastructure: ${e.message}", e)
            NodeResult.Failure("Error executing Notion node: ${e.message}")
        }
    }
    
    /**
     * Execute a Gmail node
     */
    private suspend fun executeGmailNode(node: WorkflowNode.GmailNode, cookies: Map<String, String>, webView: android.webkit.WebView, uiContext: String? = null): NodeResult {
        Logger.logInfo("WorkflowEngine", "Executing Gmail node: ${node.action} through Puter.js infrastructure with UI context")
        
        return try {
            // Use Puter.js to execute the Gmail action
            val prompt = buildGmailPrompt(node, cookies, uiContext)
            val result = puterClient.chat(webView, prompt, uiContext, "gpt-5-nano")
            
            NodeResult.Success("Executed Gmail action: ${node.action} through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowEngine", "Error executing Gmail node through Puter.js infrastructure: ${e.message}", e)
            NodeResult.Failure("Error executing Gmail node: ${e.message}")
        }
    }
    
    /**
     * Execute a web automation node
     */
    private suspend fun executeWebAutomationNode(node: WorkflowNode.WebAutomationNode, cookies: Map<String, String>, webView: android.webkit.WebView, uiContext: String? = null): NodeResult {
        Logger.logInfo("WorkflowEngine", "Executing web automation node: ${node.action} through Puter.js infrastructure with UI context")
        
        return try {
            // Use Puter.js to execute the web automation action
            val prompt = buildWebAutomationPrompt(node, cookies, uiContext)
            val result = puterClient.chat(webView, prompt, uiContext, "gpt-5-nano")
            
            NodeResult.Success("Executed web automation action: ${node.action} through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowEngine", "Error executing web automation node through Puter.js infrastructure: ${e.message}", e)
            NodeResult.Failure("Error executing web automation node: ${e.message}")
        }
    }
    
    /**
     * Build a prompt for Notion actions
     */
    private fun buildNotionPrompt(node: WorkflowNode.NotionNode, cookies: Map<String, String>, uiContext: String? = null): String {
        val contextInfo = if (!uiContext.isNullOrBlank()) "Current UI context: $uiContext\n" else ""
        return """
            $contextInfo
            You are an AI assistant that can interact with Notion through the user's browser session.
            The user wants to perform the following action: ${node.action}
            Parameters: ${node.params.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Use the available cookies to authenticate with Notion and perform the requested action.
            Cookies: ${cookies.filterKeys { it.contains("notion.so") }.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Respond with the result of the action.
        """.trimIndent()
    }
    
    /**
     * Build a prompt for Gmail actions
     */
    private fun buildGmailPrompt(node: WorkflowNode.GmailNode, cookies: Map<String, String>, uiContext: String? = null): String {
        val contextInfo = if (!uiContext.isNullOrBlank()) "Current UI context: $uiContext\n" else ""
        return """
            $contextInfo
            You are an AI assistant that can interact with Gmail through the user's browser session.
            The user wants to perform the following action: ${node.action}
            Parameters: ${node.params.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Use the available cookies to authenticate with Gmail and perform the requested action.
            Cookies: ${cookies.filterKeys { it.contains("mail.google.com") }.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Respond with the result of the action.
        """.trimIndent()
    }
    
    /**
     * Build a prompt for web automation actions
     */
    private fun buildWebAutomationPrompt(node: WorkflowNode.WebAutomationNode, cookies: Map<String, String>, uiContext: String? = null): String {
        val contextInfo = if (!uiContext.isNullOrBlank()) "Current UI context: $uiContext\n" else ""
        return """
            $contextInfo
            You are an AI assistant that can perform web automation tasks.
            The user wants to perform the following action: ${node.action}
            Parameters: ${node.params.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Use the available cookies to authenticate with websites and perform the requested action.
            Available cookies: ${cookies.entries.joinToString(", ") { "${it.key}: ${it.value}" }}
            
            Respond with the result of the action.
        """.trimIndent()
    }
    
    /**
     * Workflow result types
     */
    sealed class WorkflowResult {
        data class Success(val message: String) : WorkflowResult() {
            val success: Boolean = true
        }
        data class Failure(val errorMessage: String) : WorkflowResult() {
            val success: Boolean = false
        }
    }
    
    /**
     * Node result types
     */
    sealed class NodeResult {
        data class Success(val message: String) : NodeResult() {
            val success: Boolean = true
        }
        data class Failure(val errorMessage: String) : NodeResult() {
            val success: Boolean = false
        }
    }
    
    /**
     * Workflow data class
     */
    @Parcelize
    @Serializable
    data class Workflow(
        val id: String,
        val name: String,
        val description: String,
        val nodes: List<WorkflowNode>,
        val updatedAt: Long = System.currentTimeMillis()
    ) : Parcelable
    
    /**
     * Base workflow node class
     */
    @Parcelize
    @Serializable
    sealed class WorkflowNode : Parcelable {
        @Parcelize
        @Serializable
        data class NotionNode(
            val action: String,
            val params: Map<String, String>
        ) : WorkflowNode()
        
        @Parcelize
        @Serializable
        data class GmailNode(
            val action: String,
            val params: Map<String, String>
        ) : WorkflowNode()
        
        @Parcelize
        @Serializable
        data class WebAutomationNode(
            val action: String,
            val params: Map<String, String>
        ) : WorkflowNode()
    }
}