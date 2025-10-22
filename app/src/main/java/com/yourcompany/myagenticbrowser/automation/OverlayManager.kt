package com.yourcompany.myagenticbrowser.automation

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.FrameLayout
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

class OverlayManager(private val context: Context) {
    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    fun initializeOverlay() {
        Logger.logInfo("OverlayManager", "Initializing overlay")
        
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        val overlayView = LayoutInflater.from(context).inflate(R.layout.automation_indicator_layout, null)
        this.overlayView = overlayView

        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )
        }

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.x = 0
        params.y = 100

        layoutParams = params
    }

    fun showOverlay() {
        Logger.logInfo("OverlayManager", "Showing overlay")
        try {
            if (overlayView != null && windowManager != null && layoutParams != null) {
                if (overlayView?.parent == null) {
                    windowManager?.addView(overlayView, layoutParams)
                }
            }
        } catch (e: Exception) {
            Logger.logError("OverlayManager", "Error showing overlay", e)
        }
    }

    fun hideOverlay() {
        Logger.logInfo("OverlayManager", "Hiding overlay")
        try {
            if (overlayView != null && windowManager != null) {
                windowManager?.removeView(overlayView)
            }
        } catch (e: Exception) {
            Logger.logError("OverlayManager", "Error hiding overlay", e)
        }
    }
    
    fun getDebugInfo(): String {
        return "OverlayManager ready"
    }
    
    companion object {
        private var instance: OverlayManager? = null
        
        fun initialize(context: Context) {
            if (instance == null) {
                instance = OverlayManager(context)
                instance?.initializeOverlay()
            }
        }
        
        fun show(context: Context) {
            if (instance == null) {
                initialize(context)
            }
            instance?.showOverlay()
        }
        
        fun hide(context: Context) {
            instance?.hideOverlay()
        }
        
        fun getDebugInfo(): String {
            return instance?.getDebugInfo() ?: "OverlayManager not initialized"
        }
    }
}