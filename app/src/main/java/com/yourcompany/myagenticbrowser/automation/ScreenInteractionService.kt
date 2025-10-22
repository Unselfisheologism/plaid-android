package com.yourcompany.myagenticbrowser.automation

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Accessibility service for UI automation through Android Accessibility API
 * This service enables the AI agent to interact with UI elements when cookie access isn't possible
 */
class ScreenInteractionService : AccessibilityService() {
    companion object {
        var instance: ScreenInteractionService? = null
            private set
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        Logger.logInfo("ScreenInteractionService", "Accessibility service connected. UI automation capabilities are now available through Puter.js infrastructure.")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle accessibility events
        // This is where we would process events if needed
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    /**
     * Find UI element by text content
     */
    fun findElementByText(text: String): AccessibilityNodeInfo? {
        val root = rootInActiveWindow ?: return null
        return findElementByTextRecursive(root, text)
    }

    private fun findElementByTextRecursive(node: AccessibilityNodeInfo?, text: String): AccessibilityNodeInfo? {
        if (node == null) return null

        // Check if this node contains the text we're looking for
        if (node.text?.toString()?.contains(text, ignoreCase = true) == true ||
            node.contentDescription?.toString()?.contains(text, ignoreCase = true) == true) {
            return node
        }

        // Recursively search children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            val result = findElementByTextRecursive(child, text)
            if (result != null) {
                return result
            }
        }

        return null
    }

    /**
     * Find UI element by view ID
     */
    fun findElementById(viewId: String): AccessibilityNodeInfo? {
        val root = rootInActiveWindow ?: return null
        return findElementByIdRecursive(root, viewId)
    }

    /**
     * Find UI element by resource name (more specific than view ID)
     */
    fun findElementByResourceName(resourceName: String): AccessibilityNodeInfo? {
        val root = rootInActiveWindow ?: return null
        return findElementByResourceNameRecursive(root, resourceName)
    }

    /**
     * Find all UI elements by text content
     */
    fun findElementsByText(text: String): List<AccessibilityNodeInfo> {
        val root = rootInActiveWindow ?: return emptyList()
        val results = mutableListOf<AccessibilityNodeInfo>()
        findElementsByTextRecursive(root, text, results)
        return results
    }

    private fun findElementByIdRecursive(node: AccessibilityNodeInfo?, viewId: String): AccessibilityNodeInfo? {
        if (node == null) return null

        // Check if this node has the ID we're looking for
        if (node.viewIdResourceName == viewId) {
            return node
        }

        // Recursively search children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            val result = findElementByIdRecursive(child, viewId)
            if (result != null) {
                return result
            }
        }

        return null
    }

    private fun findElementByResourceNameRecursive(node: AccessibilityNodeInfo?, resourceName: String): AccessibilityNodeInfo? {
        if (node == null) return null

        // Check if this node has the resource name we're looking for
        if (node.viewIdResourceName?.contains(resourceName) == true) {
            return node
        }

        // Recursively search children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            val result = findElementByResourceNameRecursive(child, resourceName)
            if (result != null) {
                return result
            }
        }

        return null
    }

    private fun findElementsByTextRecursive(node: AccessibilityNodeInfo?, text: String, results: MutableList<AccessibilityNodeInfo>) {
        if (node == null) return

        // Check if this node contains the text we're looking for
        if (node.text?.toString()?.contains(text, ignoreCase = true) == true ||
            node.contentDescription?.toString()?.contains(text, ignoreCase = true) == true) {
            results.add(node)
        }

        // Recursively search children
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            findElementsByTextRecursive(child, text, results)
        }
    }

    /**
     * Perform click action on an element
     */
    fun clickElement(element: AccessibilityNodeInfo?): Boolean {
        if (element == null) return false

        // Check if the element is clickable
        if (element.isClickable) {
            element.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
            return true
        }

        // If not directly clickable, try to find a clickable parent
        var parent = element.parent
        while (parent != null) {
            if (parent.isClickable) {
                parent.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                return true
            }
            parent = parent.parent
        }

        return false
    }

    /**
     * Perform text input on an element
     */
    fun inputText(element: AccessibilityNodeInfo?, text: String): Boolean {
        if (element == null) return false

        // Check if the element is editable
        if (element.isEditable) {
            val bundle = android.os.Bundle()
            bundle.putCharSequence(
                android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                text
            )
            return element.performAction(
                android.view.accessibility.AccessibilityNodeInfo.ACTION_SET_TEXT,
                bundle
            )
        }

        return false
    }

    /**
     * Perform a swipe gesture in the specified direction
     */
    fun performSwipe(direction: Finger.SwipeDirection): Boolean {
        val root = rootInActiveWindow ?: return false

        // Get the bounds of the current window
        val windowBounds = android.graphics.Rect()
        root.getBoundsInScreen(windowBounds)

        // Calculate start and end coordinates based on direction
        val centerX = windowBounds.centerX()
        val centerY = windowBounds.centerY()
        val swipeDistance = windowBounds.height() / 4 // 25% of screen height

        // Ensure we don't go out of bounds
        val startX = centerX.coerceIn(windowBounds.left, windowBounds.right)
        val startY = centerY.coerceIn(windowBounds.top, windowBounds.bottom)
        val endX: Int
        val endY: Int

        when (direction) {
            Finger.SwipeDirection.UP -> {
                endX = centerX.coerceIn(windowBounds.left, windowBounds.right)
                endY = (centerY - swipeDistance).coerceIn(windowBounds.top, windowBounds.bottom)
            }
            Finger.SwipeDirection.DOWN -> {
                endX = centerX.coerceIn(windowBounds.left, windowBounds.right)
                endY = (centerY + swipeDistance).coerceIn(windowBounds.top, windowBounds.bottom)
            }
            Finger.SwipeDirection.LEFT -> {
                endX = (centerX - swipeDistance).coerceIn(windowBounds.left, windowBounds.right)
                endY = centerY.coerceIn(windowBounds.top, windowBounds.bottom)
            }
            Finger.SwipeDirection.RIGHT -> {
                endX = (centerX + swipeDistance).coerceIn(windowBounds.left, windowBounds.right)
                endY = centerY.coerceIn(windowBounds.top, windowBounds.bottom)
            }
        }

        // Perform the swipe gesture
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val strokeDescription = android.accessibilityservice.GestureDescription.StrokeDescription(
                android.graphics.Path().apply {
                    moveTo(startX.toFloat(), startY.toFloat())
                    lineTo(endX.toFloat(), endY.toFloat())
                },
                0, // Start time for the stroke
                500 // 500ms duration for the swipe
            )
            
            val gestureStart = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(strokeDescription)
                .build()
            
            dispatchGesture(gestureStart, object : android.accessibilityservice.AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: android.accessibilityservice.GestureDescription?) {
                    Logger.logInfo("ScreenInteractionService", "Swipe gesture completed successfully")
                }

                override fun onCancelled(gestureDescription: android.accessibilityservice.GestureDescription?) {
                    Logger.logError("ScreenInteractionService", "Swipe gesture cancelled")
                }
            }, null)
        } else {
            // For older Android versions, we can't perform complex gestures
            Logger.logError("ScreenInteractionService", "Swipe gestures not supported on this Android version (${android.os.Build.VERSION.SDK_INT})")
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        Logger.logInfo("ScreenInteractionService", "Accessibility service destroyed. UI automation capabilities are no longer available through Puter.js infrastructure.")
    }
}