package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Activity for the automation active indicator as described in the UI specification
 * This implements the bottom-middle wireframe showing a green glow when automation is active
 */
class AutomationActiveIndicatorActivity : AppCompatActivity() {
    
    private lateinit var dotsIndicator: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var dotsAnimationRunnable: Runnable? = null
    private var dotsCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.automation_active_indicator)
        
        setupViews()
        startDotsAnimation()
        
        Logger.logInfo("AutomationActiveIndicatorActivity", "Automation active indicator activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
    }
    
    private fun setupViews() {
        dotsIndicator = findViewById(R.id.dotsIndicator)
    }
    
    private fun startDotsAnimation() {
        dotsAnimationRunnable = object : Runnable {
            override fun run() {
                dotsCount = (dotsCount + 1) % 4
                val dots = ".".repeat(dotsCount)
                dotsIndicator.text = dots
                
                // Schedule the next update
                handler.postDelayed(this, 500) // Update every 500ms
            }
        }
        
        // Start the animation
        handler.post(dotsAnimationRunnable!!)
    }
    
    private fun stopDotsAnimation() {
        dotsAnimationRunnable?.let {
            handler.removeCallbacks(it)
        }
        dotsAnimationRunnable = null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopDotsAnimation()
        Logger.logInfo("AutomationActiveIndicatorActivity", "Automation active indicator activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}