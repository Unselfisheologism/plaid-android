package com.yourcompany.myagenticbrowser.workflow

import android.content.Context
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import java.io.File

/**
 * Workflow storage for persistent workflow management
 * Stores workflows in JSON format in the app's private storage
 */
object WorkflowStorage {
    private const val WORKFLOWS_FILE = "workflows.json"
    private val json = Json { ignoreUnknownKeys = true }
    
    /**
     * Save a workflow to storage
     */
    fun saveWorkflow(context: Context, workflow: WorkflowEngine.Workflow) {
        try {
            val workflows = loadAllWorkflows(context).toMutableList()
            
            // Replace existing workflow if it exists
            val existingIndex = workflows.indexOfFirst { it.id == workflow.id }
            if (existingIndex >= 0) {
                workflows[existingIndex] = workflow
            } else {
                workflows.add(workflow)
            }
            
            // Save to file
            val json = Json.encodeToString(WorkflowList(workflows))
            context.openFileOutput(WORKFLOWS_FILE, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            
            Logger.logInfo("WorkflowStorage", "Saved workflow: ${workflow.name} through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error saving workflow through Puter.js infrastructure: ${e.message}", e)
        }
    }
    
    /**
     * Load a specific workflow by ID
     */
    fun loadWorkflow(context: Context, workflowId: String): WorkflowEngine.Workflow? {
        return try {
            loadAllWorkflows(context).firstOrNull { it.id == workflowId }
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error loading workflow through Puter.js infrastructure: ${e.message}", e)
            null
        }
    }
    
    /**
     * Load all workflows from storage
     */
    fun loadAllWorkflows(context: Context): List<WorkflowEngine.Workflow> {
        return try {
            context.openFileInput(WORKFLOWS_FILE).use { input ->
                val json = input.bufferedReader().use { it.readText() }
                Json.decodeFromString<WorkflowList>(json).workflows
            }
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error loading all workflows through Puter.js infrastructure: ${e.message}", e)
            emptyList()
        }
    }
    
    /**
     * Delete a workflow by ID
     */
    fun deleteWorkflow(context: Context, workflowId: String) {
        try {
            val workflows = loadAllWorkflows(context).filter { it.id != workflowId }
            saveAllWorkflows(context, workflows)
            Logger.logInfo("WorkflowStorage", "Deleted workflow: $workflowId through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error deleting workflow through Puter.js infrastructure: ${e.message}", e)
        }
    }
    
    /**
     * Save all workflows to storage
     */
    private fun saveAllWorkflows(context: Context, workflows: List<WorkflowEngine.Workflow>) {
        try {
            val json = Json.encodeToString(WorkflowList(workflows))
            context.openFileOutput(WORKFLOWS_FILE, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error saving all workflows through Puter.js infrastructure: ${e.message}", e)
        }
    }
    
    /**
     * Serialization classes
     */
    @Serializable
    data class WorkflowList(val workflows: List<WorkflowEngine.Workflow>)
}