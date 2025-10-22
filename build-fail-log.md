Run ./gradlew clean assembleDebug
  ./gradlew clean assembleDebug
  shell: /usr/bin/bash -e {0}
  env:
    JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.16-8/x64
    JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.16-8/x64
Downloading https://services.gradle.org/distributions/gradle-8.2-bin.zip
............10%............20%............30%.............40%............50%............60%............70%.............80%............90%............100%
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
> Task :app:checkDebugAarMetadata
> Task :app:dataBindingGenBaseClassesDebug
> Task :app:mapDebugSourceSetPaths
> Task :app:createDebugCompatibleScreenManifests
> Task :app:extractDeepLinksDebug
> Task :app:processDebugMainManifest
> Task :app:processDebugManifest
> Task :app:javaPreCompileDebug
> Task :app:mergeDebugShaders
> Task :app:compileDebugShaders NO-SOURCE
> Task :app:processDebugManifestForPackage
> Task :app:generateDebugAssets UP-TO-DATE
> Task :app:mergeDebugAssets
> Task :app:compressDebugAssets
> Task :app:processDebugResources
> Task :app:checkDebugDuplicateClasses
> Task :app:desugarDebugFileDependencies
> Task :app:compileDebugKotlin
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/agent/AiAgent.kt:98:73 Unresolved reference: performNaturalLanguageSearch
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:7:16 Unresolved reference: parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:8:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:9:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:246:2 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:247:2 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:247:2 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:248:6 Class 'SearchTurn' is not abstract and does not implement abstract member public abstract fun describeContents(): Int defined in android.os.Parcelable
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:257:2 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:258:2 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:258:2 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:259:6 Class 'SearchResults' is not abstract and does not implement abstract member public abstract fun describeContents(): Int defined in android.os.Parcelable
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:274:2 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:275:2 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:275:2 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/PuterSearchOrchestrator.kt:276:6 Class 'SearchSource' is not abstract and does not implement abstract member public abstract fun describeContents(): Int defined in android.os.Parcelable
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/browser/cookies/CookieManager.kt:16:58 No value passed for parameter 'p1'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:17:32 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:18:32 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:19:32 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:20:32 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:36:16 Not enough information to infer type variable T
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:37:16 Not enough information to infer type variable T
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:38:16 Not enough information to infer type variable T
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:39:16 Not enough information to infer type variable T
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:48:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:48:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:49:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:49:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:50:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:50:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:51:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:51:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:54:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:54:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:55:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:55:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:56:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:56:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:57:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:57:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:60:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:60:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:61:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:61:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:62:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:62:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:63:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:63:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:66:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:66:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:67:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:67:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:68:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:68:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:69:30 Variable expected
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/ui/AutomationActiveIndicatorActivity.kt:69:43 Unresolved reference: View
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:10:16 Unresolved reference: parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:11:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:12:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:13:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:14:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:15:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:36:29 Unresolved reference: success
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:37:85 Unresolved reference: errorMessage
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:189:6 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:190:6 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:190:6 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:191:10 Class 'Workflow' is not abstract and does not implement abstract member public abstract fun describeContents(): Int defined in android.os.Parcelable
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:202:6 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:203:6 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:203:6 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:205:10 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:206:10 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:206:10 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:207:14 Class 'NotionNode' is not abstract and does not implement abstract base class member public abstract fun describeContents(): Int defined in com.yourcompany.myagenticbrowser.workflow.WorkflowEngine.WorkflowNode
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:212:10 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:213:10 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:213:10 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:214:14 Class 'GmailNode' is not abstract and does not implement abstract base class member public abstract fun describeContents(): Int defined in com.yourcompany.myagenticbrowser.workflow.WorkflowEngine.WorkflowNode
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:219:10 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:220:10 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:220:10 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowEngine.kt:221:14 Class 'WebAutomationNode' is not abstract and does not implement abstract base class member public abstract fun describeContents(): Int defined in com.yourcompany.myagenticbrowser.workflow.WorkflowEngine.WorkflowNode
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:6:16 Unresolved reference: parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:7:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:8:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:9:16 Unresolved reference: serialization
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:18:24 Unresolved reference: Json
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:18:31 Unresolved reference: ignoreUnknownKeys
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:36:24 Unresolved reference: Json
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:66:17 Unresolved reference: Json
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:92:24 Unresolved reference: Json
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:104:6 Unresolved reference: Parcelize
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:105:6 Cannot access 'Serializable': it is internal in 'kotlin.io'
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:105:6 This class does not have a constructor
e: file:///home/runner/work/plaid-android/plaid-android/app/src/main/java/com/yourcompany/myagenticbrowser/workflow/WorkflowStorage.kt:106:10 Class 'WorkflowList' is not abstract and does not implement abstract member public abstract fun describeContents(): Int defined in android.os.Parcelable
> Task :app:compileDebugKotlin FAILED
> Task :app:mergeExtDexDebug
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
BUILD FAILED in 3m 5s
24 actionable tasks: 23 executed, 1 up-to-date
Error: Process completed with exit code 1.