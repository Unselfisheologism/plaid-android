package com.yourcompany.myagenticbrowser.browser.tab

import android.content.Context
import com.yourcompany.myagenticbrowser.utilities.Logger
import java.util.*

enum class TabOwner {
    USER, AGENT
}

enum class TabStatus {
    ACTIVE, DORMANT
}

data class TabState(
    val id: String = UUID.randomUUID().toString(),
    val url: String = "https://www.google.com",
    val title: String = "New Tab",
    val timestamp: Long = System.currentTimeMillis(),
    val owner: TabOwner = TabOwner.USER,
    val status: TabStatus = TabStatus.ACTIVE
)

class TabManager(private val context: Context) {
    private val tabs = mutableListOf<TabState>()
    
    fun addTab(url: String = "https://www.google.com", owner: TabOwner = TabOwner.USER, status: TabStatus = TabStatus.ACTIVE) {
        tabs.add(TabState(url = url, owner = owner, status = status))
        Logger.logInfo("TabManager", "Added tab: $url, Owner: $owner, Status: $status, Total tabs: ${tabs.size}")
    }
    
    fun removeTabAt(index: Int) {
        if (index >= 0 && index < tabs.size) {
            val removedTab = tabs.removeAt(index)
            Logger.logInfo("TabManager", "Removed tab at index $index: ${removedTab.url}, Total tabs: ${tabs.size}")
        } else {
            Logger.logError("TabManager", "Attempted to remove tab at invalid index: $index, Total tabs: ${tabs.size}")
        }
    }
    
    fun removeOldestTab() {
        if (tabs.isNotEmpty()) {
            // Find the tab with the oldest timestamp
            val oldestTab = tabs.minByOrNull { it.timestamp }
            if (oldestTab != null) {
                tabs.remove(oldestTab)
                Logger.logInfo("TabManager", "Removed oldest tab: ${oldestTab.url}")
            }
        }
    }
    
    fun getTabOwner(index: Int): TabOwner? {
        return if (index >= 0 && index < tabs.size) {
            tabs[index].owner
        } else {
            null
        }
    }
    
    fun getTabStatus(index: Int): TabStatus? {
        return if (index >= 0 && index < tabs.size) {
            tabs[index].status
        } else {
            null
        }
    }
    
    fun updateTabOwner(index: Int, owner: TabOwner) {
        if (index >= 0 && index < tabs.size) {
            tabs[index] = tabs[index].copy(owner = owner)
            Logger.logInfo("TabManager", "Updated tab owner at index $index to $owner")
        } else {
            Logger.logError("TabManager", "Attempted to update owner at invalid index: $index")
        }
    }
    
    fun updateTabStatus(index: Int, status: TabStatus) {
        if (index >= 0 && index < tabs.size) {
            tabs[index] = tabs[index].copy(status = status)
            Logger.logInfo("TabManager", "Updated tab status at index $index to $status")
        } else {
            Logger.logError("TabManager", "Attempted to update status at invalid index: $index")
        }
    }
    
    fun getTabsByOwner(owner: TabOwner): List<Pair<Int, TabState>> {
        return tabs.mapIndexedNotNull { index, tab ->
            if (tab.owner == owner) index to tab else null
        }
    }
    
    fun getTabAt(index: Int): TabState {
        if (index < 0 || index >= tabs.size) {
            Logger.logError("TabManager", "Attempted to access invalid tab index: $index, Total tabs: ${tabs.size}")
            // Return a default tab state if index is invalid
            return TabState()
        }
        return tabs[index]
    }
    
    fun getTabCount(): Int = tabs.size
    
    fun getAllTabs(): List<TabState> = tabs.toList()
    
    fun updateTabTitle(index: Int, title: String) {
        if (index >= 0 && index < tabs.size) {
            tabs[index] = tabs[index].copy(title = title)
            Logger.logInfo("TabManager", "Updated tab title at index $index: $title")
        } else {
            Logger.logError("TabManager", "Attempted to update title at invalid index: $index")
        }
    }
    
    fun updateTabUrl(index: Int, url: String) {
        if (index >= 0 && index < tabs.size) {
            tabs[index] = tabs[index].copy(url = url)
            Logger.logInfo("TabManager", "Updated tab URL at index $index: $url")
        } else {
            Logger.logError("TabManager", "Attempted to update URL at invalid index: $index")
        }
    }
}