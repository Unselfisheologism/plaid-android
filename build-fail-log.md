Run ./gradlew clean assembleDebug

Welcome to Gradle 8.2!

Here are the highlights of this release:
 - Kotlin DSL: new reference documentation, assignment syntax by default
 - Kotlin DSL is now the default with Gradle init
 - Improved suggestions to resolve errors in console output

For more details see https://docs.gradle.org/8.2/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)
Checking the license for package Android SDK Build-Tools 33.0.1 in /usr/local/lib/android/sdk/licenses
License for package Android SDK Build-Tools 33.0.1 accepted.
Preparing "Install Android SDK Build-Tools 33.0.1 v.33.0.1".
"Install Android SDK Build-Tools 33.0.1 v.33.0.1" ready.
Installing Android SDK Build-Tools 33.0.1 in /usr/local/lib/android/sdk/build-tools/33.0.1
"Install Android SDK Build-Tools 33.0.1 v.33.0.1" complete.
"Install Android SDK Build-Tools 33.0.1 v.33.0.1" finished.
> Task :clean
> Task :app:clean UP-TO-DATE
> Task :app:preBuild UP-TO-DATE
> Task :app:preDebugBuild UP-TO-DATE
> Task :app:mergeDebugNativeDebugMetadata NO-SOURCE
> Task :app:generateDebugResValues
> Task :app:generateDebugResources
> Task :app:dataBindingMergeDependencyArtifactsDebug
> Task :app:packageDebugResources
> Task :app:mergeDebugResources
> Task :app:mapDebugSourceSetPaths
> Task :app:parseDebugLocalResources
> Task :app:checkDebugAarMetadata
> Task :app:createDebugCompatibleScreenManifests
> Task :app:dataBindingGenBaseClassesDebug
> Task :app:extractDeepLinksDebug
> Task :app:processDebugMainManifest
> Task :app:processDebugManifest
> Task :app:javaPreCompileDebug
> Task :app:mergeDebugShaders
> Task :app:compileDebugShaders NO-SOURCE
> Task :app:generateDebugAssets UP-TO-DATE
> Task :app:mergeDebugAssets
> Task :app:compressDebugAssets
> Task :app:desugarDebugFileDependencies
> Task :app:mergeDebugJniLibFolders
> Task :app:checkDebugDuplicateClasses
> Task :app:mergeDebugNativeLibs
> Task :app:mergeLibDexDebug

> Task :app:stripDebugDebugSymbols
Unable to strip the following libraries, packaging them as they are: libimage_processing_util_jni.so.

> Task :app:validateSigningDebug
> Task :app:writeDebugAppMetadata
> Task :app:writeDebugSigningConfigVersions
> Task :app:processDebugManifestForPackage
> Task :app:processDebugResources
> Task :app:mergeExtDexDebug

e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:209:41 Cannot access 'puterAuthHelper': it is private in 'BrowserActivity'
> Task :app:compileDebugKotlin FAILED
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:142:1 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:8:8 Unresolved reference: okhttp3
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:9:8 Unresolved reference: okhttp3
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:10:8 Unresolved reference: okhttp3
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:15:26 Unresolved reference: OkHttpClient
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:48:24 Unresolved reference: FormBody
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:55:23 Unresolved reference: Request
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:60:50 Unresolved reference: Callback
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:61:13 'onFailure' overrides nothing
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:61:42 Unresolved reference: Call
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:68:13 'onResponse' overrides nothing
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:68:43 Unresolved reference: Call
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:68:59 Unresolved reference: Response
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:72:36 None of the following functions can be called with the arguments supplied: 
public constructor JSONObject(p0: String!) defined in org.json.JSONObject
public constructor JSONObject(p0: (Mutable)Map<(raw) Any?, (raw) Any?>!) defined in org.json.JSONObject
public constructor JSONObject(p0: JSONTokener!) defined in org.json.JSONObject
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:99:24 Unresolved reference: FormBody
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:106:23 Unresolved reference: Request
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:111:9 Unresolved reference: client
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:111:50 Unresolved reference: Callback
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:112:13 'onFailure' overrides nothing
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:112:42 Unresolved reference: Call
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:113:17 Unresolved reference: runOnUiThread
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:114:40 Unresolved reference: @AuthActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:115:21 Unresolved reference: finish
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:119:13 'onResponse' overrides nothing
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:119:43 Unresolved reference: Call
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:119:59 Unresolved reference: Response
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:120:17 Unresolved reference: runOnUiThread
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:123:36 None of the following functions can be called with the arguments supplied: 
public constructor JSONObject(p0: String!) defined in org.json.JSONObject
public constructor JSONObject(p0: (Mutable)Map<(raw) Any?, (raw) Any?>!) defined in org.json.JSONObject
public constructor JSONObject(p0: JSONTokener!) defined in org.json.JSONObject
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:126:44 Unresolved reference: @AuthActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:128:44 Unresolved reference: @AuthActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:130:21 Unresolved reference: finish
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:137:21 Unresolved reference: getSharedPreferences
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthActivity.kt:137:62 Unresolved reference: MODE_PRIVATE
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:38:17 Unresolved reference: updateAuthStatus
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:188:5 Conflicting overloads: protected open fun onDestroy(): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity, protected open fun onDestroy(): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:194:5 Conflicting overloads: protected open fun onDestroy(): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity, protected open fun onDestroy(): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/PuterJSInterface.kt:151:37 Cannot access 'puterAuthHelper': it is private in 'BrowserActivity'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/PuterJSInterface.kt:167:23 Cannot access 'puterAuthHelper': it is private in 'BrowserActivity'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/WebViewFragment.kt:120:45 Cannot access 'tabManager': it is private in 'BrowserActivity'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/WebViewFragment.kt:198:45 Cannot access 'tabManager': it is private in 'BrowserActivity'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:200:55 Unresolved reference: CustomWebChromeClient
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/TabPreviewActivity.kt:29:39 Cannot access 'tabManager': it is private in 'BrowserActivity'

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:compileDebugKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details

* Try:
31 actionable tasks: 30 executed, 1 up-to-date
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

BUILD FAILED in 1m 16s
Error: Process completed with exit code 1.
