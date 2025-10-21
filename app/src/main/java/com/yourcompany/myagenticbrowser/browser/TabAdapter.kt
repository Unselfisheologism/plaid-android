package com.yourcompany.myagenticbrowser.browser

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yourcompany.myagenticbrowser.browser.tab.TabManager

class TabAdapter(
    fa: FragmentActivity,
    private val tabManager: TabManager
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = tabManager.getTabCount()

    override fun createFragment(position: Int): Fragment {
        return WebViewFragment.newInstance(tabManager.getTabAt(position).url, position)
    }

    fun addTab(owner: com.yourcompany.myagenticbrowser.browser.tab.TabOwner = com.yourcompany.myagenticbrowser.browser.tab.TabOwner.USER) {
        tabManager.addTab(owner = owner)
        notifyItemInserted(itemCount - 1)
    }

    fun removeTab(position: Int) {
        tabManager.removeTabAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }
}