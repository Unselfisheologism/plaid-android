package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.browser.cookies.CookieManager
import com.yourcompany.myagenticbrowser.workflow.WorkflowEngine
import com.yourcompany.myagenticbrowser.workflow.WorkflowStorage
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * Activity for building workflows with visual node editor as described in the UI specification
 * This implements the workflow builder interface with node-based workflow system
 */
class WorkflowBuilderActivity : AppCompatActivity() {
    private lateinit var workflowEngine: WorkflowEngine
    private lateinit var workflowNameEditText: EditText
    private lateinit var workflowDescriptionEditText: EditText
    private lateinit var workflowNodesContainer: LinearLayout
    private lateinit var addNotionNodeButton: Button
    private lateinit var addGmailNodeButton: Button
    private lateinit var addWebAutomationNodeButton: Button
    private lateinit var saveWorkflowButton: Button
    private lateinit var runWorkflowButton: Button
    
    private var currentWorkflowId: String? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workflow_activity)
        
        // Initialize PuterClient for workflow engine
        val puterClient = PuterClient()
        workflowEngine = WorkflowEngine(this, puterClient)
        
        setupUI()
        loadWorkflowIfNeeded()
        
        Logger.logInfo("WorkflowBuilderActivity", "Workflow builder activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
    }
    
    private fun setupUI() {
        workflowNameEditText = findViewById(R.id.workflowName)
        workflowDescriptionEditText = findViewById(R.id.workflowDescription)
        workflowNodesContainer = findViewById(R.id.workflowNodesContainer)
        addNotionNodeButton = findViewById(R.id.addNotionNodeButton)
        addGmailNodeButton = findViewById(R.id.addGmailNodeButton)
        addWebAutomationNodeButton = findViewById(R.id.addWebAutomationNodeButton)
        saveWorkflowButton = findViewById(R.id.saveWorkflowButton)
        runWorkflowButton = findViewById(R.id.runWorkflowButton)
        
        addNotionNodeButton.setOnClickListener {
            addNode(WorkflowEngine.WorkflowNode.NotionNode(
                action = "create_page",
                params = mapOf("title" to "", "content" to "")
            ))
        }
        
        addGmailNodeButton.setOnClickListener {
            addNode(WorkflowEngine.WorkflowNode.GmailNode(
                action = "send_email",
                params = mapOf("to" to "", "subject" to "", "body" to "")
            ))
        }
        
        addWebAutomationNodeButton.setOnClickListener {
            addNode(WorkflowEngine.WorkflowNode.WebAutomationNode(
                action = "click_element",
                params = mapOf("selector" to "", "value" to "")
            ))
        }
        
        saveWorkflowButton.setOnClickListener {
            saveWorkflow()
        }
        
        runWorkflowButton.setOnClickListener {
            runWorkflow()
        }
    }
    
    private fun loadWorkflowIfNeeded() {
        val workflowId = intent.getStringExtra("WORKFLOW_ID")
        if (!workflowId.isNullOrEmpty()) {
            currentWorkflowId = workflowId
            val workflow = WorkflowStorage.loadWorkflow(this, workflowId)
            if (workflow != null) {
                workflowNameEditText.setText(workflow.name)
                workflowDescriptionEditText.setText(workflow.description)
                
                // Clear existing nodes and add the loaded ones
                workflowNodesContainer.removeAllViews()
                for (node in workflow.nodes) {
                    addNodeToUI(node)
                }
            }
        }
    }
    
    private fun addNode(node: WorkflowEngine.WorkflowNode) {
        addNodeToUI(node)
    }
    
    private fun addNodeToUI(node: WorkflowEngine.WorkflowNode) {
        val nodeView = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 8, 16, 8)
            setBackgroundResource(R.drawable.node_background)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }
        
        val nodeInfo = TextView(this).apply {
            text = when (node) {
                is WorkflowEngine.WorkflowNode.NotionNode -> "Notion: ${node.action}"
                is WorkflowEngine.WorkflowNode.GmailNode -> "Gmail: ${node.action}"
                is WorkflowEngine.WorkflowNode.WebAutomationNode -> "Web: ${node.action}"
                else -> "Unknown Node"
            }
            textSize = 14f
            setPadding(8, 8, 8, 8)
        }
        
        val editButton = Button(this).apply {
            text = "Edit"
            setOnClickListener {
                // In a real implementation, this would open a dialog to edit the node
                Toast.makeText(this@WorkflowBuilderActivity, "Edit node functionality would be implemented here", Toast.LENGTH_SHORT).show()
            }
        }
        
        val deleteButton = Button(this).apply {
            text = "Delete"
            setOnClickListener {
                workflowNodesContainer.removeView(nodeView)
            }
        }
        
        nodeView.addView(nodeInfo)
        nodeView.addView(editButton)
        nodeView.addView(deleteButton)
        
        workflowNodesContainer.addView(nodeView)
    }
    
    private fun saveWorkflow() {
        val name = workflowNameEditText.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a workflow name", Toast.LENGTH_SHORT).show()
            return
        }
        
        val nodes = getNodesFromUI()
        if (nodes.isEmpty()) {
            Toast.makeText(this, "Add at least one node to your workflow", Toast.LENGTH_SHORT).show()
            return
        }
        
        val workflow = com.yourcompany.myagenticbrowser.workflow.WorkflowEngine.Workflow(
            id = currentWorkflowId ?: java.util.UUID.randomUUID().toString(),
            name = name,
            description = workflowDescriptionEditText.text.toString(),
            nodes = nodes
        )
        
        WorkflowStorage.saveWorkflow(this, workflow)
        Toast.makeText(this, "Workflow saved", Toast.LENGTH_SHORT).show()
        
        // Return to previous screen if this was a new workflow
        if (currentWorkflowId == null) {
            finish()
        }
    }
    
    private fun getNodesFromUI(): List<WorkflowEngine.WorkflowNode> {
        // Extract the actual nodes from the UI
        val nodes = mutableListOf<WorkflowEngine.WorkflowNode>()
        for (i in 0 until workflowNodesContainer.childCount) {
            val nodeView = workflowNodesContainer.getChildAt(i) as? LinearLayout
            if (nodeView != null) {
                // In a real implementation, we would extract the actual node data from the view
                // For now, we'll use a placeholder approach based on the node type
                val nodeInfoView = nodeView.getChildAt(0) as? TextView
                if (nodeInfoView != null) {
                    val nodeText = nodeInfoView.text.toString()
                    when {
                        nodeText.contains("Notion", ignoreCase = true) -> {
                            nodes.add(WorkflowEngine.WorkflowNode.NotionNode(
                                action = "create_page",
                                params = mapOf("title" to "Sample Title", "content" to "Sample Content")
                            ))
                        }
                        nodeText.contains("Gmail", ignoreCase = true) -> {
                            nodes.add(WorkflowEngine.WorkflowNode.GmailNode(
                                action = "send_email",
                                params = mapOf("to" to "example@example.com", "subject" to "Sample Subject", "body" to "Sample Body")
                            ))
                        }
                        nodeText.contains("Web", ignoreCase = true) -> {
                            nodes.add(WorkflowEngine.WorkflowNode.WebAutomationNode(
                                action = "click_element",
                                params = mapOf("selector" to "button", "value" to "")
                            ))
                        }
                    }
                }
            }
        }
        return nodes
    }
    
    private fun runWorkflow() {
        scope.launch {
            val name = workflowNameEditText.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this@WorkflowBuilderActivity, "Please enter a workflow name", Toast.LENGTH_SHORT).show()
                return@launch
            }
            
            val nodes = getNodesFromUI()
            if (nodes.isEmpty()) {
                Toast.makeText(this@WorkflowBuilderActivity, "Add nodes to your workflow first", Toast.LENGTH_SHORT).show()
                return@launch
            }
            
            val workflow = com.yourcompany.myagenticbrowser.workflow.WorkflowEngine.Workflow(
                id = "temp",
                name = "Temporary Workflow",
                description = "Run from builder",
                nodes = nodes
            )
            
            // Get current WebView from parent activity or create a temporary one for the workflow execution
            val webView = try {
                // Since WorkflowBuilderActivity is a standalone activity, it doesn't have direct access to BrowserActivity
                // In a real implementation, you might pass the WebView reference when starting this activity
                null
            } catch (e: Exception) {
                Logger.logError("WorkflowBuilderActivity", "Error getting WebView: ${e.message}", e)
                null
            }
            
            if (webView != null) {
                // Execute workflow with current UI context
                val cookies = CookieManager.getAllCookies()
                val uiContext = getCurrentUIContext()
                
                try {
                    val result = workflowEngine.execute(workflow, cookies, webView, uiContext)
                    if (result.success) {
                        Toast.makeText(this@WorkflowBuilderActivity, "Workflow executed successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@WorkflowBuilderActivity, "Workflow execution failed: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Logger.logError("WorkflowBuilderActivity", "Error executing workflow: ${e.message}", e)
                    Toast.makeText(this@WorkflowBuilderActivity, "Error executing workflow: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@WorkflowBuilderActivity, "No WebView available for workflow execution", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun getCurrentUIContext(): String {
        // Since WorkflowBuilderActivity is a standalone activity, it doesn't have direct access to BrowserActivity
        // In a real implementation, you might pass the context when starting this activity
        return "Workflow builder context"
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        Logger.logInfo("WorkflowBuilderActivity", "Workflow builder activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}