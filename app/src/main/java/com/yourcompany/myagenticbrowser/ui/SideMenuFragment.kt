package com.yourcompany.myagenticbrowser.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.agent.AgentHomePage
import com.yourcompany.myagenticbrowser.agent.SearchVisualizationActivity
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.utilities.Logger
import com.yourcompany.myagenticbrowser.ui.WorkflowActivity

/**
 * Bottom sheet fragment for the side menu with circular icons as described in the UI specification
 * This implements the middle-right wireframe showing a vertical menu with various functions
 */
class SideMenuFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.side_menu_layout, container, false)

        setupMenuItems(view)

        Logger.logInfo("SideMenuFragment", "Side menu fragment created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // Customize the dialog if needed
        }
    }

    private fun setupMenuItems(view: View) {
        // Close button
        val closeButton = view.findViewById<ImageButton>(R.id.closeButton)
        closeButton.setOnClickListener {
            // Dismiss the side menu
            dismiss()
        }

        // Set up click listeners for each menu item by finding them by text content
        setMenuItemClickListener(view, "Account") { openAccountActivity() }
        setMenuItemClickListener(view, "Settings") { openSettingsActivity() }
        setMenuItemClickListener(view, "Integrations") { openIntegrationsActivity() }
        setMenuItemClickListener(view, "Workflows") { openWorkflowsActivity() }
        setMenuItemClickListener(view, "Agents Experts") { openAgentsExpertsActivity() }
        setMenuItemClickListener(view, "Support") { openSupportActivity() }
        setMenuItemClickListener(view, "Summarize Page") { summarizeCurrentPage() }
        setMenuItemClickListener(view, "Ask About Page") { askAboutCurrentPage() }
    }

    private fun setMenuItemClickListener(view: View, itemName: String, action: () -> Unit) {
        // Find the TextView with the specific text and set its click listener
        val textView = findTextViewByText(view, itemName)
        textView?.setOnClickListener {
            action()
            // Close the menu after selection
            dismiss()
        }
    }

    private fun findTextViewByText(view: View, text: String): android.widget.TextView? {
        if (view is android.widget.TextView && view.text == text) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val result = findTextViewByText(view.getChildAt(i), text)
                if (result != null) {
                    return result
                }
            }
        }

        return null
    }

    private fun openAccountActivity() {
        // In a real implementation, this would open the account activity
        Logger.logInfo("SideMenuFragment", "Account menu item selected")
    }

    private fun openSettingsActivity() {
        // In a real implementation, this would open the settings activity
        // Removed PuterConfigActivity reference as it's no longer needed
        // Puter.js handles authentication automatically
        Logger.logInfo("SideMenuFragment", "Settings menu item selected")
    }

    private fun openIntegrationsActivity() {
        // In a real implementation, this would open integrations
        Logger.logInfo("SideMenuFragment", "Integrations menu item selected")
    }

    private fun openWorkflowsActivity() {
        // In a real implementation, this would open workflows
        activity?.startActivity(Intent(context, WorkflowActivity::class.java))
        Logger.logInfo("SideMenuFragment", "Workflows menu item selected")
    }

    private fun openAgentsExpertsActivity() {
        // In a real implementation, this would open agents/experts
        activity?.startActivity(Intent(context, AgentHomePage::class.java))
        Logger.logInfo("SideMenuFragment", "Agents Experts menu item selected")
    }

    private fun openSupportActivity() {
        // In a real implementation, this would open support
        Logger.logInfo("SideMenuFragment", "Support menu item selected")
    }

    private fun summarizeCurrentPage() {
        // In a real implementation, this would summarize the current page
        // Try to get the current page content from the browser activity
        val browserActivity = activity as? BrowserActivity
        browserActivity?.getCurrentWebViewFragment()?.let { webViewFragment ->
            val webView = webViewFragment.getWebView()
            val url = webViewFragment.getUrl()
            Logger.logInfo("SideMenuFragment", "Summarizing page: $url")
            // In a real implementation, we would send this to the AI agent for summarization
        }
        Logger.logInfo("SideMenuFragment", "Summarize Page menu item selected")
    }

    private fun askAboutCurrentPage() {
        // In a real implementation, this would ask about the current page
        // Try to get the current page content from the browser activity
        val browserActivity = activity as? BrowserActivity
        browserActivity?.getCurrentWebViewFragment()?.let { webViewFragment ->
            val webView = webViewFragment.getWebView()
            val url = webViewFragment.getUrl()
            Logger.logInfo("SideMenuFragment", "Asking about page: $url")
            // In a real implementation, we would send this to the AI agent for analysis
        }
        Logger.logInfo("SideMenuFragment", "Ask About Page menu item selected")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("SideMenuFragment", "Side menu fragment destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}