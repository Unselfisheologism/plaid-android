# Authentication Best Practices for Android Mobile App with puter.js

## Recommendation: Use Chrome Custom Tabs, Not WebView

You should **definitely use Chrome Custom Tabs (browser redirect)** rather than WebView-based authentication. WebView authentication is strongly discouraged because it is "susceptible to credential interception" . The OAuth 2.0 standard specifically states that "authorization requests from native apps should only be made through external user agents, primarily the user's browser" .

Using WebView to handle OAuth 2.0 requests "will negatively impact the application's security and usability" . Since the release of Chrome Custom Tabs, "Google has recommended that developers move away from using WebViews for authentication" .

## No Web Server Needed

You **do not need to create a web server** to handle the authentication endpoints. The AUTH_URL (`https://puter.com/api/auth`) is already provided by puter.com's API, and your mobile app only needs to:
1. Redirect the user to this endpoint
2. Handle the callback to your redirect URI
3. Exchange the authorization code for a token

## Kotlin Implementation

### 1. AndroidManifest.xml - Add Intent Filter

```xml
<activity
    android:name=".AuthActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="myagenticbrowser" android:host="auth" />
    </intent-filter>
</activity>
```

### 2. Launch Authentication with Chrome Custom Tabs

```kotlin
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun launchAuthentication() {
    val clientId = "myagenticbrowser"
    val redirectUri = "myagenticbrowser://auth"
    val authUrl = "https://puter.com/api/auth?" +
            "client_id=$clientId&" +
            "redirect_uri=${Uri.encode(redirectUri)}&" +
            "response_type=code"

    val customTabsIntent = CustomTabsIntent.Builder()
        .setToolbarColor(getColor(R.color.primaryColor))
        .build()
    
    customTabsIntent.launchUrl(this, Uri.parse(authUrl))
}
```

Using Chrome Custom Tabs "will give the user a sense that they don't leave your app because it has a toolbar that you can customize to match your app" .

### 3. Handle Redirect in AuthActivity

```kotlin
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleRedirect(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleRedirect(intent)
    }

    private fun handleRedirect(intent: Intent?) {
        val data = intent?.data ?: run {
            finish()
            return
        }

        if (data.scheme == "myagenticbrowser" && data.host == "auth") {
            val code = data.getQueryParameter("code")
            if (!code.isNullOrEmpty()) {
                exchangeCodeForToken(code)
            }
        }
        finish()
    }

    private fun exchangeCodeForToken(code: String) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("code", code)
            .add("client_id", "myagenticbrowser")
            .add("redirect_uri", "myagenticbrowser://auth")
            .add("grant_type", "authorization_code")
            .build()

        val request = Request.Builder()
            .url("https://puter.com/api/token")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle error
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val json = JSONObject(responseBody)
                    val token = json.getString("access_token")
                    saveToken(token)
                }
            }
        })
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("puter_auth_prefs", MODE_PRIVATE)
        prefs.edit()
            .putString("puter_auth_token", token)
            .apply()
    }
}
```

### 4. Retrieve Token When Needed

```kotlin
fun getAuthToken(): String? {
    val prefs = getSharedPreferences("puter_auth_prefs", MODE_PRIVATE)
    return prefs.getString("puter_auth_token", null)
}
```

This implementation follows OAuth 2.0 best practices for native Android applications and provides a secure authentication flow without requiring you to host any additional web servers.