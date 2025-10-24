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
> Task :app:parseDebugLocalResources
> Task :app:mapDebugSourceSetPaths
> Task :app:checkDebugAarMetadata
> Task :app:dataBindingGenBaseClassesDebug
> Task :app:createDebugCompatibleScreenManifests
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
> Task :app:processDebugManifestForPackage
> Task :app:checkDebugDuplicateClasses
> Task :app:mergeLibDexDebug
> Task :app:mergeDebugNativeLibs
> Task :app:processDebugResources
> Task :app:validateSigningDebug
> Task :app:writeDebugAppMetadata
> Task :app:writeDebugSigningConfigVersions
> Task :app:stripDebugDebugSymbols
Unable to strip the following libraries, packaging them as they are: libimage_processing_util_jni.so.
> Task :app:mergeExtDexDebug
> Task :app:compileDebugKotlin FAILED
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:9 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:16 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:17 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:35 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:36 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:42 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:44 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:352:48 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:355:9 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:355:16 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:355:17 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:355:39 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:355:46 Name expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:365:10 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:365:12 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:365:13 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:365:29 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:365:30 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:400:9 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:480:1 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:358:17 Unresolved reference: isAuthenticated
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:363:17 Unresolved reference: isAuthenticated
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:368:31 Suspend function 'suspendCancellableCoroutine' should be called only from a coroutine or another suspend function
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:369:13 Unresolved reference: webView
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:371:17 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:374:21 Unresolved reference: isAuthenticated
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:378:21 Unresolved reference: webView
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:391:25 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:393:25 Unresolved reference: isAuthenticated
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterClient.kt:425:32 Unresolved reference: PUTER_JS_URL
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/PuterJSInterface.kt:137:25 Unresolved reference: loadPuterJS
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/WebViewFragment.kt:115:54 Unresolved reference: getPuterJSScript
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:13 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:20 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:21 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:39 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:40 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:46 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:48 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:294:52 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:297:13 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:297:20 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:297:21 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:297:43 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:297:50 Name expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:307:14 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:307:16 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:307:17 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:307:33 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:307:34 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:340:13 Expecting member declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:341:11 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:341:16 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:344:5 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:462:1 Expecting a top level declaration
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:186:25 Unresolved reference: loadPuterJS
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:219:45 No value passed for parameter 'onCancellation'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:237:49 No value passed for parameter 'onCancellation'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:310:35 Suspend function 'suspendCancellableCoroutine' should be called only from a coroutine or another suspend function
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:311:17 Unresolved reference: webView
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:313:21 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:316:45 No value passed for parameter 'onCancellation'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:319:25 Unresolved reference: webView
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:332:29 Cannot infer a type for this parameter. Please specify it explicitly.
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:334:49 No value passed for parameter 'onCancellation'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:341:16 Function declaration must have a name
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:349:34 Unresolved reference: activity
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:362:13 Unresolved reference: puterClient
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:366:17 Unresolved reference: puterClient
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:385:40 Unresolved reference: context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:393:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:396:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:397:13 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:397:47 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:403:36 Unresolved reference: context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:413:39 Unresolved reference: context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:422:39 Unresolved reference: context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:430:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:431:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:432:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:435:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:436:13 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:436:47 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:441:34 Unresolved reference: context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:449:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:452:9 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:453:13 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:453:47 Unresolved reference: chatMessagesContainer
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:457:5 Modifier 'override' is not applicable to 'top level function'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:458:9 No supertypes are accessible in this context
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/ChatBottomSheetFragment.kt:459:9 Unresolved reference: scope
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:30:29 Unresolved reference: loadPuterJS
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':app:compileDebugKotlin'.
31 actionable tasks: 30 executed, 1 up-to-date
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details
* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.
BUILD FAILED in 1m 11s
Error: Process completed with exit code 1.