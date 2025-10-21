package com.yourcompany.myagenticbrowser.automation

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * A rewritten Finger class that uses the AccessibilityService for all actions, requiring no root access.
 * This class adapts Blurr's Finger functionality for browser context to enable UI automation
 * when cookie access isn't possible, all through Puter.js infrastructure
 */
class Finger(private val context: Context) {
    private val TAG = "Finger (Accessibility)"

    // A helper to safely get the service instance
    private val service: ScreenInteractionService?
        get() {
            val instance = ScreenInteractionService.instance
            if (instance == null) {
                Logger.logError(TAG, "ScreenInteractionService is not running or not connected!")
            }
            return instance
        }

    /**
     * Click on an element by text content
     */
    fun clickByText(text: String): Boolean {
        val service = service ?: return false
        val element = service.findElementByText(text)
        if (element != null) {
            val result = service.clickElement(element)
            if (result) {
                Logger.logInfo(TAG, "Successfully clicked element with text: $text")
            } else {
                Logger.logError(TAG, "Failed to click element with text: $text")
            }
            return result
        } else {
            Logger.logError(TAG, "Could not find element with text: $text")
            return false
        }
    }

    /**
     * Click on an element by view ID
     */
    fun clickById(viewId: String): Boolean {
        val service = service ?: return false
        val element = service.findElementById(viewId)
        if (element != null) {
            val result = service.clickElement(element)
            if (result) {
                Logger.logInfo(TAG, "Successfully clicked element with ID: $viewId")
            } else {
                Logger.logError(TAG, "Failed to click element with ID: $viewId")
            }
            return result
        } else {
            Logger.logError(TAG, "Could not find element with ID: $viewId")
            return false
        }
    }

    /**
     * Input text into an element by text content
     */
    fun inputText(targetText: String, input: String): Boolean {
        val service = service ?: return false
        val element = service.findElementByText(targetText)
        if (element != null) {
            val result = service.inputText(element, input)
            if (result) {
                Logger.logInfo(TAG, "Successfully input text '$input' into element with text: $targetText")
            } else {
                Logger.logError(TAG, "Failed to input text '$input' into element with text: $targetText")
            }
            return result
        } else {
            Logger.logError(TAG, "Could not find element with text: $targetText")
            return false
        }
    }

    /**
     * Input text into an element by view ID
     */
    fun inputTextById(viewId: String, input: String): Boolean {
        val service = service ?: return false
        val element = service.findElementById(viewId)
        if (element != null) {
            val result = service.inputText(element, input)
            if (result) {
                Logger.logInfo(TAG, "Successfully input text '$input' into element with ID: $viewId")
            } else {
                Logger.logError(TAG, "Failed to input text '$input' into element with ID: $viewId")
            }
            return result
        } else {
            Logger.logError(TAG, "Could not find element with ID: $viewId")
            return false
        }
    }

    /**
     * Find an element by text content
     */
    fun findElementByText(text: String): AccessibilityNodeInfo? {
        val service = service ?: return null
        return service.findElementByText(text)
    }

    /**
     * Find an element by view ID
     */
    fun findElementById(viewId: String): AccessibilityNodeInfo? {
        val service = service ?: return null
        return service.findElementById(viewId)
    }

    /**
     * Find elements by text content (returns all matching elements)
     */
    fun findElementsByText(text: String): List<AccessibilityNodeInfo> {
        val service = service ?: return emptyList()
        return service.findElementsByText(text)
    }

    /**
     * Click on an element by its resource name (more specific than view ID)
     */
    fun clickByResourceName(resourceName: String): Boolean {
        val service = service ?: return false
        val element = service.findElementByResourceName(resourceName)
        if (element != null) {
            val result = service.clickElement(element)
            if (result) {
                Logger.logInfo(TAG, "Successfully clicked element with resource name: $resourceName")
            } else {
                Logger.logError(TAG, "Failed to click element with resource name: $resourceName")
            }
            return result
        } else {
            Logger.logError(TAG, "Could not find element with resource name: $resourceName")
            return false
        }
    }

    /**
     * Perform a swipe gesture (up, down, left, right)
     */
    fun swipe(direction: SwipeDirection): Boolean {
        val service = service ?: return false
        return service.performSwipe(direction)
    }

    /**
     * Wait for an element to appear with a timeout
     */
    suspend fun waitForElementByText(text: String, timeoutMs: Long = 5000): Boolean {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            if (findElementByText(text) != null) {
                return true
            }
            kotlinx.coroutines.delay(500) // Wait 50ms before checking again
        }
        return false
    }

    /**
     * Get debug information about the current accessibility service state
     */
    fun getDebugInfo(): String {
        val service = service
        return if (service != null) {
            "Accessibility service is connected. Root window: ${if (service.rootInActiveWindow != null) "available" else "null"}"
        } else {
            "Accessibility service is NOT connected. Automation will not work until service is enabled."
        }
    }

    /**
     * Swipe direction enum
     */
    enum class SwipeDirection {
        UP, DOWN, LEFT, RIGHT
    }
}