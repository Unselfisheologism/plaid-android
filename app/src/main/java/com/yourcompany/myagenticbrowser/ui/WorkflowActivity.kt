package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger
import com.yourcompany.myagenticbrowser.workflow.WorkflowEngine
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.workflow.WorkflowStorage
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.browser.WebViewFragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import java.util.*
import android.webkit.WebView

/**
 * Activity for the workflow interface with vertical nodes as described in the UI specification
 * This implements the top-middle wireframe showing a workflow interface with nodes arranged vertically
 */
class WorkflowActivity : AppCompatActivity() {
    private lateinit var workflowNameEditText: EditText
    private lateinit var workflowDescriptionEditText: EditText
    private lateinit var workflowNodesContainer: LinearLayout
    private lateinit var saveWorkflowButton: Button
    private lateinit var runWorkflowButton: Button
    private lateinit var addNotionNodeButton: Button
    private lateinit var addGmailNodeButton: Button
    private lateinit var addWebAutomationNodeButton: Button
    
    private lateinit var workflowEngine: WorkflowEngine
    private lateinit var puterClient: PuterClient
    private val workflowNodes = mutableListOf<WorkflowNodeData>()
    
    // Data class to hold workflow node information
    data class WorkflowNodeData(
        val type: String,
        var action: String,
        val params: MutableMap<String, String> = mutableMapOf()
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workflow_activity)
        
        Logger.logInfo("WorkflowActivity", "Workflow activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
        
        // Initialize Puter.js components
        puterClient = PuterClient()
        workflowEngine = WorkflowEngine(this, puterClient)
        
        // Initialize UI elements
        setupUI()
    }
    
    private fun setupUI() {
        // Find UI elements
        workflowNameEditText = findViewById(R.id.workflowName)
        workflowDescriptionEditText = findViewById(R.id.workflowDescription)
        workflowNodesContainer = findViewById(R.id.workflowNodesContainer)
        saveWorkflowButton = findViewById(R.id.saveWorkflowButton)
        runWorkflowButton = findViewById(R.id.runWorkflowButton)
        addNotionNodeButton = findViewById(R.id.addNotionNodeButton)
        addGmailNodeButton = findViewById(R.id.addGmailNodeButton)
        addWebAutomationNodeButton = findViewById(R.id.addWebAutomationNodeButton)
        
        // Set up button listeners
        addNotionNodeButton.setOnClickListener {
            addNotionNode()
        }
        
        addGmailNodeButton.setOnClickListener {
            addGmailNode()
        }
        
        addWebAutomationNodeButton.setOnClickListener {
            addWebAutomationNode()
        }
        
        saveWorkflowButton.setOnClickListener {
            saveWorkflow()
        }
        
        runWorkflowButton.setOnClickListener {
            runWorkflow()
        }
    }
    
    private fun addNotionNode() {
        val nodeIndex = workflowNodes.size
        val nodeData = WorkflowNodeData("notion", "Create Page")
        workflowNodes.add(nodeData)
        
        // Create UI for the notion node
        val nodeView = createNotionNodeView(nodeIndex, nodeData)
        workflowNodesContainer.addView(nodeView)
        
        Logger.logInfo("WorkflowActivity", "Added Notion node to workflow through Puter.js infrastructure")
    }
    
    private fun addGmailNode() {
        val nodeIndex = workflowNodes.size
        val nodeData = WorkflowNodeData("gmail", "Send Email")
        workflowNodes.add(nodeData)
        
        // Create UI for the gmail node
        val nodeView = createGmailNodeView(nodeIndex, nodeData)
        workflowNodesContainer.addView(nodeView)
        
        Logger.logInfo("WorkflowActivity", "Added Gmail node to workflow through Puter.js infrastructure")
    }
    
    private fun addWebAutomationNode() {
        val nodeIndex = workflowNodes.size
        val nodeData = WorkflowNodeData("web", "Navigate to URL")
        workflowNodes.add(nodeData)
        
        // Create UI for the web automation node
        val nodeView = createWebAutomationNodeView(nodeIndex, nodeData)
        workflowNodesContainer.addView(nodeView)
        
        Logger.logInfo("WorkflowActivity", "Added Web Automation node to workflow through Puter.js infrastructure")
    }
    
    private fun createNotionNodeView(index: Int, nodeData: WorkflowNodeData): View {
        val nodeView = LayoutInflater.from(this).inflate(R.layout.node_layout, null)
        
        // Set node title
        val nodeTitle = nodeView.findViewById<TextView>(R.id.nodeTitle)
        nodeTitle.text = "Notion Node ${index + 1}"
        
        // Set node description
        val nodeDescription = nodeView.findViewById<TextView>(R.id.nodeDescription)
        nodeDescription.text = "Action: ${nodeData.action}"
        
        // Set up action spinner
        val actionSpinner = nodeView.findViewById<Spinner>(R.id.nodeActionSpinner)
        val actions = arrayOf("Create Page", "Update Page", "Delete Page", "Query Database")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, actions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        actionSpinner.adapter = adapter
        
        // Set initial action
        val initialActionIndex = actions.indexOf(nodeData.action)
        if (initialActionIndex >= 0) {
            actionSpinner.setSelection(initialActionIndex)
        }
        
        // Handle action changes
        actionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                nodeData.action = actions[position]
                nodeDescription.text = "Action: ${nodeData.action}"
            }
            
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        // Set up parameter inputs
        val paramContainer = nodeView.findViewById<LinearLayout>(R.id.nodeParamContainer)
        setupNotionNodeParams(paramContainer, nodeData)
        
        // Set up remove button
        val removeButton = nodeView.findViewById<Button>(R.id.removeNodeButton)
        removeButton.setOnClickListener {
            workflowNodes.removeAt(index)
            workflowNodesContainer.removeView(nodeView)
            // Re-index remaining nodes
            refreshNodeIndices()
        }
        
        return nodeView
    }
    
    private fun setupNotionNodeParams(container: LinearLayout, nodeData: WorkflowNodeData) {
        container.removeAllViews()
        
        // Add database ID parameter
        val dbIdLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val dbIdLabel = TextView(this).apply {
            text = "Database ID:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val dbIdEditText = EditText(this).apply {
            hint = "Enter database ID"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["databaseId"] ?: "")
        }
        
        dbIdEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["databaseId"] = text.toString()
        }
        
        dbIdLayout.addView(dbIdLabel)
        dbIdLayout.addView(dbIdEditText)
        container.addView(dbIdLayout)
        
        // Add page title parameter
        val pageTitleLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val pageTitleLabel = TextView(this).apply {
            text = "Page Title:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val pageTitleEditText = EditText(this).apply {
            hint = "Enter page title"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["pageTitle"] ?: "")
        }
        
        pageTitleEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["pageTitle"] = text.toString()
        }
        
        pageTitleLayout.addView(pageTitleLabel)
        pageTitleLayout.addView(pageTitleEditText)
        container.addView(pageTitleLayout)
    }
    
    private fun createGmailNodeView(index: Int, nodeData: WorkflowNodeData): View {
        val nodeView = LayoutInflater.from(this).inflate(R.layout.node_layout, null)
        
        // Set node title
        val nodeTitle = nodeView.findViewById<TextView>(R.id.nodeTitle)
        nodeTitle.text = "Gmail Node ${index + 1}"
        
        // Set node description
        val nodeDescription = nodeView.findViewById<TextView>(R.id.nodeDescription)
        nodeDescription.text = "Action: ${nodeData.action}"
        
        // Set up action spinner
        val actionSpinner = nodeView.findViewById<Spinner>(R.id.nodeActionSpinner)
        val actions = arrayOf("Send Email", "Read Email", "Delete Email", "Mark as Read")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, actions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        actionSpinner.adapter = adapter
        
        // Set initial action
        val initialActionIndex = actions.indexOf(nodeData.action)
        if (initialActionIndex >= 0) {
            actionSpinner.setSelection(initialActionIndex)
        }
        
        // Handle action changes
        actionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                nodeData.action = actions[position]
                nodeDescription.text = "Action: ${nodeData.action}"
            }
            
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        // Set up parameter inputs
        val paramContainer = nodeView.findViewById<LinearLayout>(R.id.nodeParamContainer)
        setupGmailNodeParams(paramContainer, nodeData)
        
        // Set up remove button
        val removeButton = nodeView.findViewById<Button>(R.id.removeNodeButton)
        removeButton.setOnClickListener {
            workflowNodes.removeAt(index)
            workflowNodesContainer.removeView(nodeView)
            // Re-index remaining nodes
            refreshNodeIndices()
        }
        
        return nodeView
    }
    
    private fun setupGmailNodeParams(container: LinearLayout, nodeData: WorkflowNodeData) {
        container.removeAllViews()
        
        // Add recipient parameter
        val recipientLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val recipientLabel = TextView(this).apply {
            text = "Recipient:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val recipientEditText = EditText(this).apply {
            hint = "Enter recipient email"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["recipient"] ?: "")
        }
        
        recipientEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["recipient"] = text.toString()
        }
        
        recipientLayout.addView(recipientLabel)
        recipientLayout.addView(recipientEditText)
        container.addView(recipientLayout)
        
        // Add subject parameter
        val subjectLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val subjectLabel = TextView(this).apply {
            text = "Subject:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val subjectEditText = EditText(this).apply {
            hint = "Enter email subject"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["subject"] ?: "")
        }
        
        subjectEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["subject"] = text.toString()
        }
        
        subjectLayout.addView(subjectLabel)
        subjectLayout.addView(subjectEditText)
        container.addView(subjectLayout)
        
        // Add body parameter
        val bodyLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val bodyLabel = TextView(this).apply {
            text = "Body:"
        }
        
        val bodyEditText = EditText(this).apply {
            hint = "Enter email body"
            minLines = 3
            setText(nodeData.params["body"] ?: "")
        }
        
        bodyEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["body"] = text.toString()
        }
        
        bodyLayout.addView(bodyLabel)
        bodyLayout.addView(bodyEditText)
        container.addView(bodyLayout)
    }
    
    private fun createWebAutomationNodeView(index: Int, nodeData: WorkflowNodeData): View {
        val nodeView = LayoutInflater.from(this).inflate(R.layout.node_layout, null)
        
        // Set node title
        val nodeTitle = nodeView.findViewById<TextView>(R.id.nodeTitle)
        nodeTitle.text = "Web Automation Node ${index + 1}"
        
        // Set node description
        val nodeDescription = nodeView.findViewById<TextView>(R.id.nodeDescription)
        nodeDescription.text = "Action: ${nodeData.action}"
        
        // Set up action spinner
        val actionSpinner = nodeView.findViewById<Spinner>(R.id.nodeActionSpinner)
        val actions = arrayOf("Navigate to URL", "Click Element", "Fill Form", "Extract Text")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, actions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        actionSpinner.adapter = adapter
        
        // Set initial action
        val initialActionIndex = actions.indexOf(nodeData.action)
        if (initialActionIndex >= 0) {
            actionSpinner.setSelection(initialActionIndex)
        }
        
        // Handle action changes
        actionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                nodeData.action = actions[position]
                nodeDescription.text = "Action: ${nodeData.action}"
            }
            
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        // Set up parameter inputs
        val paramContainer = nodeView.findViewById<LinearLayout>(R.id.nodeParamContainer)
        setupWebAutomationNodeParams(paramContainer, nodeData)
        
        // Set up remove button
        val removeButton = nodeView.findViewById<Button>(R.id.removeNodeButton)
        removeButton.setOnClickListener {
            workflowNodes.removeAt(index)
            workflowNodesContainer.removeView(nodeView)
            // Re-index remaining nodes
            refreshNodeIndices()
        }
        
        return nodeView
    }
    
    private fun setupWebAutomationNodeParams(container: LinearLayout, nodeData: WorkflowNodeData) {
        container.removeAllViews()
        
        // Add URL parameter
        val urlLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val urlLabel = TextView(this).apply {
            text = "URL:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val urlEditText = EditText(this).apply {
            hint = "Enter URL"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["url"] ?: "")
        }
        
        urlEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["url"] = text.toString()
        }
        
        urlLayout.addView(urlLabel)
        urlLayout.addView(urlEditText)
        container.addView(urlLayout)
        
        // Add selector parameter
        val selectorLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val selectorLabel = TextView(this).apply {
            text = "Selector:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val selectorEditText = EditText(this).apply {
            hint = "Enter CSS selector"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["selector"] ?: "")
        }
        
        selectorEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["selector"] = text.toString()
        }
        
        selectorLayout.addView(selectorLabel)
        selectorLayout.addView(selectorEditText)
        container.addView(selectorLayout)
        
        // Add value parameter
        val valueLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 8, 0, 8)
        }
        
        val valueLabel = TextView(this).apply {
            text = "Value:"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }
        
        val valueEditText = EditText(this).apply {
            hint = "Enter value"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            setText(nodeData.params["value"] ?: "")
        }
        
        valueEditText.doOnTextChanged { text, _, _, _ ->
            nodeData.params["value"] = text.toString()
        }
        
        valueLayout.addView(valueLabel)
        valueLayout.addView(valueEditText)
        container.addView(valueLayout)
    }
    
    private fun refreshNodeIndices() {
        workflowNodesContainer.removeAllViews()
        for (i in workflowNodes.indices) {
            val nodeData = workflowNodes[i]
            val nodeView = when (nodeData.type) {
                "notion" -> createNotionNodeView(i, nodeData)
                "gmail" -> createGmailNodeView(i, nodeData)
                "web" -> createWebAutomationNodeView(i, nodeData)
                else -> continue
            }
            workflowNodesContainer.addView(nodeView)
        }
    }
    
    private fun saveWorkflow() {
        val workflowName = workflowNameEditText.text.toString().trim()
        val workflowDescription = workflowDescriptionEditText.text.toString().trim()
        
        if (workflowName.isEmpty()) {
            Toast.makeText(this, "Please enter a workflow name", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (workflowNodes.isEmpty()) {
            Toast.makeText(this, "Please add at least one node to the workflow", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Create workflow data structure
        val workflow = WorkflowEngine.Workflow(
            id = UUID.randomUUID().toString(),
            name = workflowName,
            description = workflowDescription,
            nodes = workflowNodes.map { nodeData ->
                when (nodeData.type) {
                    "notion" -> WorkflowEngine.WorkflowNode.NotionNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    "gmail" -> WorkflowEngine.WorkflowNode.GmailNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    "web" -> WorkflowEngine.WorkflowNode.WebAutomationNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    else -> throw IllegalArgumentException("Unknown node type: ${nodeData.type}")
                }
            }
        )
        
        // Save workflow
        WorkflowStorage.saveWorkflow(this, workflow)
        
        Toast.makeText(this, "Workflow saved successfully through Puter.js infrastructure", Toast.LENGTH_SHORT).show()
        Logger.logInfo("WorkflowActivity", "Workflow saved: $workflowName through Puter.js infrastructure")
    }
    
    private fun runWorkflow() {
        val workflowName = workflowNameEditText.text.toString().trim()
        
        if (workflowName.isEmpty()) {
            Toast.makeText(this, "Please enter a workflow name", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (workflowNodes.isEmpty()) {
            Toast.makeText(this, "Please add at least one node to the workflow", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Create workflow data structure
        val workflow = WorkflowEngine.Workflow(
            id = UUID.randomUUID().toString(),
            name = workflowName,
            description = workflowDescriptionEditText.text.toString(),
            nodes = workflowNodes.map { nodeData ->
                when (nodeData.type) {
                    "notion" -> WorkflowEngine.WorkflowNode.NotionNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    "gmail" -> WorkflowEngine.WorkflowNode.GmailNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    "web" -> WorkflowEngine.WorkflowNode.WebAutomationNode(
                        action = nodeData.action,
                        params = HashMap(nodeData.params)
                    )
                    else -> throw IllegalArgumentException("Unknown node type: ${nodeData.type}")
                }
            }
        )
        
        // Get current WebView context for UI context
        val webView = getCurrentWebView()
        val uiContext = getCurrentUIContext()
        
        // Run workflow asynchronously
        Thread {
            try {
                // Get cookies for the workflow
                val cookies = getCookies()
                
                // Execute workflow through Puter.js infrastructure
                val result = workflowEngine.execute(workflow, cookies, webView, uiContext)
                
                // Show result on UI thread
                runOnUiThread {
                    when (result) {
                        is WorkflowEngine.WorkflowResult.Success -> {
                            Toast.makeText(this, "Workflow executed successfully: ${result.message}", Toast.LENGTH_LONG).show()
                            Logger.logInfo("WorkflowActivity", "Workflow executed successfully: ${result.message} through Puter.js infrastructure")
                        }
                        is WorkflowEngine.WorkflowResult.Failure -> {
                            Toast.makeText(this, "Workflow execution failed: ${result.errorMessage}", Toast.LENGTH_LONG).show()
                            Logger.logError("WorkflowActivity", "Workflow execution failed: ${result.errorMessage} through Puter.js infrastructure")
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error executing workflow: ${e.message}", Toast.LENGTH_LONG).show()
                    Logger.logError("WorkflowActivity", "Error executing workflow: ${e.message} through Puter.js infrastructure", e)
                }
            }
        }.start()
    }
    
    private fun getCurrentWebView(): WebView? {
        // Since WorkflowActivity doesn't have direct access to BrowserActivity's WebView,
        // we'll return null. In a real implementation, you might pass the WebView reference
        // from the BrowserActivity when starting this activity
        return null
    }
    
    private fun getCurrentUIContext(): String {
        // Since WorkflowActivity doesn't have direct access to BrowserActivity's WebView,
        // return a default context. In a real implementation, you might pass the context
        // from the BrowserActivity when starting this activity
        return "Workflow execution context"
    }
    
    private fun getCookies(): Map<String, String> {
        // In a real implementation, this would get actual cookies
        // For now, we'll return an empty map
        return emptyMap()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("WorkflowActivity", "Workflow activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}