package com.yourcompany.myagenticbrowser.browser.cookies

import android.webkit.WebView
import android.webkit.CookieManager as AndroidCookieManager
import com.yourcompany.myagenticbrowser.utilities.Logger

object CookieManager {
    private val cookieManager = AndroidCookieManager.getInstance()
    
    init {
        cookieManager.setAcceptCookie(true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(WebView(null), true)
        }
    }
    
    fun getCookieForDomain(domain: String): String? {
        return try {
            cookieManager.getCookie(domain)
        } catch (e: Exception) {
            Logger.logError("CookieManager", "Error getting cookies for domain $domain: ${e.message}")
            null
        }
    }
    
    fun getAllCookies(): Map<String, String> {
        val cookiesMap = mutableMapOf<String, String>()
        
        // This is a simplified approach - in a real implementation, you'd need to
        // iterate through all known domains or use a more sophisticated approach
        // For now, we'll return a map with some common domains
        val domains = listOf("google.com", "gmail.com", "notion.so", "youtube.com", "twitter.com", "facebook.com")
        
        domains.forEach { domain ->
            val cookies = getCookieForDomain("https://$domain")
            if (!cookies.isNullOrEmpty()) {
                cookiesMap["https://$domain"] = cookies
            }
        }
        
        return cookiesMap
    }
    
    fun getGmailSession(): String? {
        val cookies = getCookieForDomain("https://mail.google.com")
        return try {
            cookies?.split(";")
                ?.find { it.trim().startsWith("SID=") }
                ?.substringAfter("SID=")
                ?.trim()
        } catch (e: Exception) {
            Logger.logError("CookieManager", "Error extracting Gmail session: ${e.message}")
            null
        }
    }
    
    fun getNotionSession(): String? {
        val cookies = getCookieForDomain("https://www.notion.so")
        return try {
            cookies?.split(";")
                ?.find { it.trim().startsWith("token_v2=") }
                ?.substringAfter("token_v2=")
                ?.trim()
        } catch (e: Exception) {
            Logger.logError("CookieManager", "Error extracting Notion session: ${e.message}")
            null
        }
    }
    
    fun setCookie(url: String, cookie: String) {
        try {
            cookieManager.setCookie(url, cookie)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush()
            }
        } catch (e: Exception) {
            Logger.logError("CookieManager", "Error setting cookie for $url: ${e.message}")
        }
    }
    
    fun removeSessionCookies() {
        try {
            cookieManager.removeAllCookies(null)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush()
            }
        } catch (e: Exception) {
            Logger.logError("CookieManager", "Error removing session cookies: ${e.message}")
        }
    }
    
    fun syncCookies() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush()
        } else {
            @Suppress("DEPRECATION")
            cookieManager.removeExpiredCookies()
        }
    }
    
    fun hasCookiesForDomain(domain: String): Boolean {
        val cookies = getCookieForDomain(domain)
        return !cookies.isNullOrEmpty()
    }
 }