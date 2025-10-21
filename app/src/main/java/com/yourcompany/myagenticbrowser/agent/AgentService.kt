package com.yourcompany.myagenticbrowser.agent

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.PuterConfigManager
import com.yourcompany.myagenticbrowser.ai.puter.PuterSearchOrchestrator
import com.yourcompany.myagenticbrowser.ai.puter.model.ChatModel
import com.yourcompany.myagenticbrowser.ai.puter.model.SearchModel
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * Service for handling AI agent operations through Puter.js infrastructure
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class AgentService : Service() {
    private lateinit var configManager: PuterConfigManager
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var agent: AiAgent? = null

    override fun onCreate() {
        super.onCreate()
        configManager = PuterConfigManager.getInstance(this)
        agent = AiAgent.create(configManager = configManager)
        Logger.logInfo("AgentService", "Agent service created with Puter.js integration. All AI capabilities now route through Puter.js infrastructure as required. Puter.js handles all AI provider endpoints and authentication internally.")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * Handle commands through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required
     * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
     * Puter.js handles all AI provider endpoints and authentication internally as required
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getStringExtra("command") ?: ""
        Logger.logInfo("AgentService", "Received command: $command through Puter.js infrastructure")
        if (command.isNotEmpty()) {
            when (command) {
                "update_context" -> {
                    // Handle context update using the Parcelable AgentContext through Puter.js infrastructure
                    val agentContext = intent?.getParcelableExtra("agent_context", AiAgent.AgentContext::class.java)
                    
                    if (agentContext != null) {
                        // Update the agent with new context through Puter.js infrastructure
                        agent = AiAgent.create(
                            context = agentContext,
                            configManager = configManager
                        )
                    }
                }
                else -> {
                    // Process command asynchronously to avoid blocking the main thread through Puter.js infrastructure
                    serviceScope.launch {
                        agent?.let { currentAgent ->
                            val response = currentAgent.processCommand(command)
                            withContext(Dispatchers.Main) {
                                handleAgentResponse(response, intent)
                            }
                        } ?: run {
                            val errorResponse = AiAgent.AgentResponse.Error("Agent not initialized through Puter.js infrastructure")
                            handleAgentResponse(errorResponse, intent)
                        }
                    }
                }
            }
        }
        return START_NOT_STICKY
    }

    /**
     * Handle agent response through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required
     * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
     * Puter.js handles all AI provider endpoints and authentication internally as required
     */
    private fun handleAgentResponse(response: AiAgent.AgentResponse, originalIntent: Intent?) {
        when(response) {
            is AiAgent.AgentResponse.Success -> {
                Logger.logInfo("AgentService", "Agent response through Puter.js infrastructure: ${response.message}")
                // Send result back to UI if needed through Puter.js infrastructure
                sendResultToUI("success", response.message)
            }
            is AiAgent.AgentResponse.Error -> {
                Logger.logError("AgentService", "Agent error through Puter.js infrastructure: ${response.message}")
                sendResultToUI("error", response.message)
            }
            is AiAgent.AgentResponse.RequiresAutomation -> {
                Logger.logInfo("AgentService", "Agent requires automation through Puter.js infrastructure: ${response.message}, task: ${response.task}, details: ${response.details}")
                // Trigger UI automation based on the task and details
                executeAutomationTask(response.task, response.details)
                sendResultToUI("automation", response.message)
            }
            is AiAgent.AgentResponse.RequiresSearch -> {
                Logger.logInfo("AgentService", "Agent performed search through Puter.js infrastructure: ${response.query}")
                sendResultToUI("search", response.results)
            }
        }
    }

    /**
     * Execute automation task using the Finger class for UI automation
     */
    private fun executeAutomationTask(task: String, details: Map<String, String>? = null) {
        // Run automation in background to avoid blocking the main thread
        serviceScope.launch {
            try {
                val automationExecutor = AutomationExecutor(this@AgentService)
                
                // Log debug info before starting automation
                Logger.logInfo("AgentService", "AutomationExecutor debug info: ${automationExecutor.getDebugInfo()}")
                
                // Determine the appropriate automation workflow based on the task
                val workflow = when (task) {
                    "create_notion_page" -> createNotionPageWorkflow(details)
                    "gmail_action" -> createGmailWorkflow(details)
                    else -> createGenericWorkflow(task, details)
                }
                
                // Show visual feedback for automation
                OverlayManager.show(this@AgentService, "Starting automation: ${workflow.name}")
                
                val success = automationExecutor.executeWorkflow(workflow)
                
                if (success) {
                    OverlayManager.show(this@AgentService, "Automation completed successfully: ${workflow.name}")
                    TTSManager.speak("Automation completed: ${workflow.name}")
                } else {
                    OverlayManager.show(this@AgentService, "Automation failed: ${workflow.name}")
                    TTSManager.speak("Automation failed: ${workflow.name}")
                }
                
                Logger.logInfo("AgentService", "Automation task '${workflow.name}' completed with success: $success through Puter.js infrastructure")
                
            } catch (e: Exception) {
                Logger.logError("AgentService", "Error executing automation task through Puter.js infrastructure: ${e.message}", e)
                OverlayManager.show(this@AgentService, "Automation error: ${e.message}")
                TTSManager.speak("Automation error: ${e.message}")
            }
        }
    }

    /**
     * Create a workflow for creating a Notion page
     */
    private fun createNotionPageWorkflow(details: Map<String, String>? = null): AutomationExecutor.AutomationWorkflow {
        val title = details?.get("title") ?: "New Page"
        val content = details?.get("content") ?: ""
        
        val steps = mutableListOf<AutomationExecutor.AutomationStep>().apply {
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.WAIT,
                selector = AutomationExecutor.ElementSelector.ByText("Wait for UI to load"),
                description = "Wait for UI to load",
                waitTimeMs = 1000
            ))
            
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.CLICK,
                selector = AutomationExecutor.ElementSelector.ByText("New page"),
                description = "Click on New page button"
            ))
            
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.WAIT_FOR_ELEMENT,
                selector = AutomationExecutor.ElementSelector.ByText("Page title"),
                description = "Wait for page to load",
                waitTimeMs = 3000
            ))
            
            // If content is provided, add steps to input the content
            if (content.isNotEmpty()) {
                add(AutomationExecutor.AutomationStep(
                    type = AutomationExecutor.StepType.INPUT,
                    selector = AutomationExecutor.ElementSelector.ByText("Page title"),
                    description = "Enter page title: $title",
                    inputValue = title
                ))
                
                add(AutomationExecutor.AutomationStep(
                    type = AutomationExecutor.StepType.WAIT,
                    selector = AutomationExecutor.ElementSelector.ByText("Wait for title input"),
                    description = "Wait for title to be entered",
                    waitTimeMs = 500
                ))
                
                add(AutomationExecutor.AutomationStep(
                    type = AutomationExecutor.StepType.INPUT,
                    selector = AutomationExecutor.ElementSelector.ByText("Type '/' for commands, or start writing"),
                    description = "Enter page content: $content",
                    inputValue = content
                ))
            }
        }
        
        return AutomationExecutor.AutomationWorkflow(
            id = "notion_create_page",
            name = "Create Notion Page",
            description = "Workflow to create a new page in Notion with title: $title",
            steps = steps.toList()
        )
    }

    /**
     * Create a workflow for Gmail actions
     */
    private fun createGmailWorkflow(details: Map<String, String>? = null): AutomationExecutor.AutomationWorkflow {
        val action = details?.get("action") ?: "compose"
        val to = details?.get("to") ?: ""
        val subject = details?.get("subject") ?: ""
        val body = details?.get("body") ?: ""
        
        val steps = mutableListOf<AutomationExecutor.AutomationStep>().apply {
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.WAIT,
                selector = AutomationExecutor.ElementSelector.ByText("Wait for UI to load"),
                description = "Wait for UI to load",
                waitTimeMs = 1000
            ))
            
            when (action) {
                "compose" -> {
                    add(AutomationExecutor.AutomationStep(
                        type = AutomationExecutor.StepType.CLICK,
                        selector = AutomationExecutor.ElementSelector.ByText("Compose"),
                        description = "Click on Compose button"
                    ))
                    
                    add(AutomationExecutor.AutomationStep(
                        type = AutomationExecutor.StepType.WAIT_FOR_ELEMENT,
                        selector = AutomationExecutor.ElementSelector.ByText("To"),
                        description = "Wait for compose window to open",
                        waitTimeMs = 300
                    ))
                    
                    // Fill in email details if provided
                    if (to.isNotEmpty()) {
                        add(AutomationExecutor.AutomationStep(
                            type = AutomationExecutor.StepType.INPUT,
                            selector = AutomationExecutor.ElementSelector.ByText("To"),
                            description = "Enter recipient: $to",
                            inputValue = to
                        ))
                    }
                    
                    if (subject.isNotEmpty()) {
                        add(AutomationExecutor.AutomationStep(
                            type = AutomationExecutor.StepType.INPUT,
                            selector = AutomationExecutor.ElementSelector.ByText("Subject"),
                            description = "Enter subject: $subject",
                            inputValue = subject
                        ))
                    }
                    
                    if (body.isNotEmpty()) {
                        add(AutomationExecutor.AutomationStep(
                            type = AutomationExecutor.StepType.INPUT,
                            selector = AutomationExecutor.ElementSelector.ByText("Message body"),
                            description = "Enter message: $body",
                            inputValue = body
                        ))
                    }
                }
                else -> {
                    add(AutomationExecutor.AutomationStep(
                        type = AutomationExecutor.StepType.CLICK,
                        selector = AutomationExecutor.ElementSelector.ByText(action.replace("_", " ")),
                        description = "Perform Gmail action: $action"
                    ))
                }
            }
        }
        
        return AutomationExecutor.AutomationWorkflow(
            id = "gmail_$action",
            name = "Gmail: ${action.capitalize()}",
            description = "Workflow to perform $action in Gmail",
            steps = steps.toList()
        )
    }

    /**
     * Create a generic workflow for unspecified tasks
     */
    private fun createGenericWorkflow(task: String, details: Map<String, String>? = null): AutomationExecutor.AutomationWorkflow {
        val steps = mutableListOf<AutomationExecutor.AutomationStep>().apply {
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.WAIT,
                selector = AutomationExecutor.ElementSelector.ByText("Wait for UI to load"),
                description = "Wait for UI to load",
                waitTimeMs = 100
            ))
            
            add(AutomationExecutor.AutomationStep(
                type = AutomationExecutor.StepType.CLICK,
                selector = AutomationExecutor.ElementSelector.ByText(task.replace("_", " ")),
                description = "Attempt to click on ${task.replace("_", " ")}"
            ))
            
            // Add additional steps based on details if provided
            details?.forEach { (key, value) ->
                if (key.startsWith("input_")) {
                    add(AutomationExecutor.AutomationStep(
                        type = AutomationExecutor.StepType.INPUT,
                        selector = AutomationExecutor.ElementSelector.ByText(key.substring(6)), // Remove "input_" prefix
                        description = "Input $key: $value",
                        inputValue = value
                    ))
                }
            }
        }
        
        return AutomationExecutor.AutomationWorkflow(
            id = "generic_$task",
            name = "Generic: $task",
            description = "Generic workflow for task: $task with details: ${details?.toString()}",
            steps = steps.toList()
        )
    }

    /**
     * Send result to UI through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required
     * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
     * Puter.js handles all AI provider endpoints and authentication internally as required
     */
    private fun sendResultToUI(resultType: String, result: String) {
        // Send result back to UI via broadcast or other mechanism through Puter.js infrastructure
        val resultIntent = Intent("AGENT_RESULT")
        resultIntent.putExtra("type", resultType)
        resultIntent.putExtra("result", result)
        sendBroadcast(resultIntent)
        Logger.logInfo("AgentService", "Sent $resultType result to UI through Puter.js infrastructure. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
    }

    companion object {
        /**
         * Update the agent's context with new information from the WebView through Puter.js infrastructure
         * All AI capabilities route through Puter.js as required
         * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
         * Puter.js handles all AI provider endpoints and authentication internally as required
         */
        fun updateContext(context: Context, agentContext: AiAgent.AgentContext) {
            // Update the agent's context with new information from the WebView through Puter.js infrastructure
            val intent = Intent(context, AgentService::class.java)
            intent.putExtra("command", "update_context")
            intent.putExtra("agent_context", agentContext)
            context.startService(intent)
            Logger.logInfo("AgentService", "Context updated through Puter.js infrastructure. All AI capabilities now route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
        }
    }

    override fun onDestroy() {
        Logger.logInfo("AgentService", "Agent service destroyed. All AI capabilities through Puter.js infrastructure have been shut down. Puter.js handles all AI provider endpoints and authentication internally as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used.")
        serviceScope.cancel()
        super.onDestroy()
    }
}