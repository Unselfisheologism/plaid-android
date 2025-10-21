package com.yourcompany.myagenticbrowser.automation

import android.content.Context
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Validator for UI automation functionality
 * This class provides methods to cross-verify that automation is working properly
 */
object UiAutomationValidator {
    
    /**
     * Validate that the UI automation components are properly set up
     */
    fun validateSetup(context: Context): ValidationResult {
        Logger.logInfo("UiAutomationValidator", "Validating UI automation setup")
        
        val results = mutableListOf<ValidationResult.ComponentResult>()
        
        // Validate Finger class
        val finger = Finger(context)
        val fingerDebugInfo = finger.getDebugInfo()
        val fingerValid = fingerDebugInfo.contains("Accessibility service is connected")
        results.add(
            ValidationResult.ComponentResult(
                name = "Finger",
                valid = fingerValid,
                message = if (fingerValid) "Finger properly connected to accessibility service" else fingerDebugInfo
            )
        )
        
        // Validate ScreenInteractionService
        val serviceConnected = ScreenInteractionService.instance != null
        val serviceValid = if (serviceConnected) {
            ScreenInteractionService.instance?.rootInActiveWindow != null
        } else {
            false
        }
        results.add(
            ValidationResult.ComponentResult(
                name = "ScreenInteractionService",
                valid = serviceValid,
                message = if (serviceValid) "Service connected and root window available" else "Service not connected or root window not available"
            )
        )
        
        // Validate AutomationExecutor
        try {
            val automationExecutor = AutomationExecutor(context)
            val executorDebugInfo = automationExecutor.getDebugInfo()
            val executorValid = executorDebugInfo.contains("initialized")
            results.add(
                ValidationResult.ComponentResult(
                    name = "AutomationExecutor",
                    valid = executorValid,
                    message = if (executorValid) "AutomationExecutor properly initialized" else executorDebugInfo
                )
            )
        } catch (e: Exception) {
            results.add(
                ValidationResult.ComponentResult(
                    name = "AutomationExecutor",
                    valid = false,
                    message = "Error initializing AutomationExecutor: ${e.message}"
                )
            )
        }
        
        // Validate OverlayManager
        try {
            OverlayManager.show(context, "Test overlay")
            results.add(
                ValidationResult.ComponentResult(
                    name = "OverlayManager",
                    valid = true,
                    message = "OverlayManager working properly"
                )
            )
            // Hide the test overlay
            OverlayManager.hide(context)
        } catch (e: Exception) {
            results.add(
                ValidationResult.ComponentResult(
                    name = "OverlayManager",
                    valid = false,
                    message = "Error with OverlayManager: ${e.message}"
                )
            )
        }
        
        val overallValid = results.all { it.valid }
        return ValidationResult(
            valid = overallValid,
            message = if (overallValid) "All UI automation components validated successfully" else "Some UI automation components failed validation",
            components = results
        )
    }
    
    /**
     * Validation result data class
     */
    data class ValidationResult(
        val valid: Boolean,
        val message: String,
        val components: List<ComponentResult>
    ) {
        data class ComponentResult(
            val name: String,
            val valid: Boolean,
            val message: String
        )
        
        fun printValidationReport() {
            Logger.logInfo("UiAutomationValidator", "=== UI AUTOMATION VALIDATION REPORT ===")
            Logger.logInfo("UiAutomationValidator", "Overall Status: ${if (valid) "PASS" else "FAIL"} - $message")
            components.forEach { component ->
                Logger.logInfo("UiAutomationValidator", "${component.name}: ${if (component.valid) "PASS" else "FAIL"} - ${component.message}")
            }
            Logger.logInfo("UiAutomationValidator", "=====================================")
        }
    }
}