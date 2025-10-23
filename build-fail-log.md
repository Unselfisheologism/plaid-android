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
> Task :app:dataBindingMergeDependencyArtifactsDebug
> Task :app:generateDebugResValues
> Task :app:generateDebugResources
> Task :app:packageDebugResources
> Task :app:mergeDebugResources
> Task :app:mapDebugSourceSetPaths
> Task :app:checkDebugAarMetadata
> Task :app:createDebugCompatibleScreenManifests
> Task :app:parseDebugLocalResources
> Task :app:extractDeepLinksDebug
> Task :app:dataBindingGenBaseClassesDebug
> Task :app:processDebugMainManifest
> Task :app:processDebugManifest
> Task :app:javaPreCompileDebug
> Task :app:mergeDebugShaders
> Task :app:compileDebugShaders NO-SOURCE
> Task :app:generateDebugAssets UP-TO-DATE
> Task :app:mergeDebugAssets
> Task :app:compressDebugAssets
> Task :app:desugarDebugFileDependencies
> Task :app:processDebugManifestForPackage
> Task :app:checkDebugDuplicateClasses
> Task :app:mergeDebugJniLibFolders
> Task :app:mergeLibDexDebug
> Task :app:mergeDebugNativeLibs
> Task :app:processDebugResources
> Task :app:validateSigningDebug
> Task :app:writeDebugAppMetadata
> Task :app:writeDebugSigningConfigVersions

> Task :app:stripDebugDebugSymbols
Unable to strip the following libraries, packaging them as they are: libimage_processing_util_jni.so.

> Task :app:mergeExtDexDebug
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:247:5 Conflicting overloads: private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity, private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity

e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:320:9 Overload resolution ambiguity: 
private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:320:37 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:340:9 Overload resolution ambiguity: 
private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:340:37 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/BrowserActivity.kt:379:5 Conflicting overloads: private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity, private final fun checkAuthenticationAndRun(action: (Boolean) -> Unit): Unit defined in com.yourcompany.myagenticbrowser.browser.BrowserActivity

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:compileDebugKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

BUILD FAILED in 46s
> Task :app:compileDebugKotlin FAILED
31 actionable tasks: 30 executed, 1 up-to-date
Error: Process completed with exit code 1.
