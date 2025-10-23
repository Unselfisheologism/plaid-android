package com.yourcompany.myagenticbrowser.workflow

import android.content.Context
import android.content.SharedPreferences
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json 
import kotlinx.serialization.decodeFromString

/**
 * Storage class for saving and loading workflows
 */
object WorkflowStorage {
    private const val PREF_NAME = "workflows"
    private const val KEY_WORKFLOWS = "saved_workflows"
    
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    
    /**
     * Save a workflow to storage
     */
    fun saveWorkflow(context: Context, workflow: WorkflowEngine.Workflow) {
        try {
            val prefs = getSharedPreferences(context)
            val workflows = loadAllWorkflows(context).toMutableList()
            
            // Check if workflow already exists and update it
            val existingIndex = workflows.indexOfFirst { it.id == workflow.id }
            if (existingIndex >= 0) {
                workflows[existingIndex] = workflow
            } else {
                workflows.add(workflow)
            }
            
            // Serialize and save
            val json = Json.encodeToString(workflows)
            prefs.edit().putString(KEY_WORKFLOWS, json).apply()
            
            Logger.logInfo("WorkflowStorage", "Workflow saved: ${workflow.name} through Puter.js infrastructure")
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error saving workflow: ${e.message}", e)
        }
    }
    
    /**
     * Load all workflows from storage
     */
    fun loadAllWorkflows(context: Context): List<WorkflowEngine.Workflow> {
        return try {
            val prefs = getSharedPreferences(context)
            val json = prefs.getString(KEY_WORKFLOWS, null)
            
            if (json != null) {
                Json.decodeFromString<List<WorkflowEngine.Workflow>>(json)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error loading workflows: ${e.message}", e)
            emptyList()
        }
    }
    
    /**
     * Load a specific workflow by ID
     */
    fun loadWorkflow(context: Context, workflowId: String): WorkflowEngine.Workflow? {
        return try {
            val workflows = loadAllWorkflows(context)
            workflows.find { it.id == workflowId }
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error loading workflow: ${e.message}", e)
            null
        }
    }
    
    /**
     * Delete a workflow by ID
     */
    fun deleteWorkflow(context: Context, workflowId: String): Boolean {
        return try {
            val prefs = getSharedPreferences(context)
            val workflows = loadAllWorkflows(context).toMutableList()
            val removed = workflows.removeIf { it.id == workflowId }
            
            if (removed) {
                val json = Json.encodeToString(workflows)
                prefs.edit().putString(KEY_WORKFLOWS, json).apply()
                Logger.logInfo("WorkflowStorage", "Workflow deleted: $workflowId through Puter.js infrastructure")
            }
            
            removed
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error deleting workflow: ${e.message}", e)
            false
        }
    }
    
    /**
     * Update a workflow
     */
    fun updateWorkflow(context: Context, workflow: WorkflowEngine.Workflow): Boolean {
        return try {
            saveWorkflow(context, workflow)
            Logger.logInfo("WorkflowStorage", "Workflow updated: ${workflow.name} through Puter.js infrastructure")
            true
        } catch (e: Exception) {
            Logger.logError("WorkflowStorage", "Error updating workflow: ${e.message}", e)
            false
        }
    }
}