package com.yourcompany.myagenticbrowser.automation

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import android.graphics.Color
import android.widget.Toast
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Overlay manager for visual notifications during UI automation
 * Provides visual feedback when automation actions are performed
 */
object OverlayManager {
    private var overlayView: HighlightView? = null
    
    /**
     * Show an overlay with a message
     */
    fun show(context: Context, message: String) {
        if (overlayView != null) return
        
        // Check if overlay permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            Logger.logError("OverlayManager", "Overlay permission not granted. Please enable 'Draw over other apps' permission for this app.")
            Toast.makeText(context, "Overlay permission required for visual feedback", Toast.LENGTH_LONG).show()
            return
        }
        
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE
            }
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            y = 100 // Offset from top
        }
        
        overlayView = HighlightView(context).apply {
            setMessage(message)
            setOnClickListener { hide(context) }
        }
        
        try {
            windowManager.addView(overlayView, params)
            
            // Auto-hide after 3 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                hide(context)
            }, 3000)
        } catch (e: Exception) {
            Logger.logError("OverlayManager", "Failed to add overlay view: ${e.message}", e)
            overlayView = null
        }
    }
    
    /**
     * Hide the overlay
     */
    fun hide(context: Context) {
        overlayView?.let { view ->
            try {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.removeView(view)
                overlayView = null
            } catch (e: Exception) {
                Logger.logError("OverlayManager", "Failed to remove overlay view: ${e.message}", e)
            }
        }
    }
    
    /**
     * Custom view for overlay display
     */
    class HighlightView(context: Context) : FrameLayout(context) {
        private val textView = TextView(context).apply {
            setBackgroundColor(Color.parseColor("#CC000000"))
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            textSize = 16f
            setPadding(32, 16, 32, 16)
            alpha = 0.9f
        }
        
        init {
            setBackgroundColor(Color.TRANSPARENT)
            addView(textView)
        }
        
        fun setMessage(message: String) {
            textView.text = message
        }
    }
}