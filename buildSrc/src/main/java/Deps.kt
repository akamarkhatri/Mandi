object AndroidConfig {
    const val compileSdkVersion = 33
    const val targetSdkVersion = 31
    const val minSdkVersion = 23
}

object Versions {
    const val coil = "2.2.2"
    const val composeBom = "2022.12.00"
    const val composeCompiler = "1.3.2"
    const val composeConstraintLayout = "1.0.1"
    const val disposable = "0.1.4"

    const val kinject = "0.1.4"
    const val kotlin = "1.7.20"
    const val kotlinxCoroutines = "1.6.4"
    const val kotlinxSerialization = "1.4.1"
    const val ktor = "2.1.2"
    const val hiltNavigationCompose = "1.0.0"
    const val androidNavigationCompose = "2.5.3"
    const val hilt = "2.43.2"
    const val hiltExt = "1.0.0"
    const val gson = "2.9.0"
    const val jUnit = "4.13.2"
}

object Plugins {
    const val android = "com.android.tools.build:gradle:7.3.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val navArgument = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidNavigationCompose}"
}

object Deps {
    val android = AndroidDeps()
    val androidx = AndroidXDeps()
    const val branchIo = "io.branch.sdk.android:library:5.2.6"
    val coil = CoilDeps()
    val konfetti = "nl.dionsegijn:konfetti:1.2.0"
    val kotlin = KotlinDeps()
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val kotlinx = KotlinXDeps()
    val ktor = KtorDeps()
    const val mockito = "org.mockito:mockito-all:1.10.19"
    const val markwon = "io.noties.markwon:core:4.6.2"
    const val mpAndroidChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:4.10.0"
    const val shimmer = "com.facebook.shimmer:shimmer:0.4.0"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val junit = "junit:junit:${Versions.jUnit}"
}

class AndroidDeps internal constructor() {
    val robolectric = "org.robolectric:robolectric:4.8.2"
    val playCore = "com.google.android.play:core:1.6.1"
    val material = "com.google.android.material:material:1.5.0"
    val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
//    val playServices = PlayServicesDeps()
}

class AndroidXDeps internal constructor() {
    val annotation = "androidx.annotation:annotation:1.5.0"
    val appcompat = "androidx.appcompat:appcompat:1.5.1"
    val composeActivity = "androidx.activity:activity-compose:1.3.1"
    val browser = "androidx.browser:browser:1.4.0"
    val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    val compose = listOf(
        // All of these use the composeBom versioning
        "androidx.compose.ui:ui",
        "androidx.compose.ui:ui-tooling-preview",
        "androidx.compose.material3:material3"
    )
    val composeDebug = listOf(
        // All of these use the composeBom versioning
        "androidx.compose.ui:ui-tooling",
        "androidx.compose.ui:ui-test-manifest"
    )
    val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    val constraintLayoutCompose =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    val core = "androidx.core:core-ktx:1.9.0"
    val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:1.0.0"
    val exifInterface = "androidx.exifinterface:exifinterface:1.3.5"
    val hiltAndroidXNavigationCompose = listOf<String>(
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}",
        "androidx.navigation:navigation-compose:${Versions.androidNavigationCompose}"
    )
//    val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltExt}"
    val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
    val lifeCycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01"
    val lifeCycleRuntimeComposektx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha01"
    val media = "androidx.media:media:1.6.0"
    val recyclerView = "androidx.recyclerview:recyclerview:1.3.0-rc01"
    val test = AndroidXTestDeps()
}

class AndroidXTestDeps internal constructor() {
    val all: List<String> = listOf(
        "androidx.test:core:1.5.0",
        "androidx.test:runner:1.5.0",
        "androidx.test:rules:1.5.0",
        "androidx.test.ext:junit:1.1.4",
        "androidx.test.ext:truth:1.5.0",
        "com.google.truth:truth:1.0.1"
    )
    val espresso = listOf(
        "androidx.test.espresso:espresso-core:3.5.0",
        "androidx.test.espresso:espresso-contrib:3.5.0",
        "androidx.test.espresso:espresso-intents:3.5.0",
        "androidx.test.espresso:espresso-accessibility:3.5.0",
        "androidx.test.espresso:espresso-web:3.5.0",
        "androidx.test.espresso.idling:idling-concurrent:3.5.0",
        "androidx.test.uiautomator:uiautomator:2.2.0"
    )
    val espressoIdlingResources = "androidx.test.espresso:espresso-idling-resource:3.4.0"
    val orchestrator = "androidx.test:orchestrator:1.4.1"
    val composeTest = "androidx.compose.ui:ui-test-junit4" // This uses the composeBom versioning
}

class KotlinDeps internal constructor() {
    val stdlib = KotlinStdLibDeps()
    val test = KotlinTestDeps()
}

class KotlinStdLibDeps internal constructor() {
    val android = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val js = "org.jetbrains.kotlin:kotlin-stdlib-js:${Versions.kotlin}"
}

class KotlinTestDeps internal constructor() {
    val common: List<String> = listOf(
        "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}",
        "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
    )
    val jvm = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    val js = "org.jetbrains.kotlin:kotlin-test-js:${Versions.kotlin}"
}

class KotlinXDeps internal constructor() {
    val coroutines = KotlinXCoroutinesDeps()
    val serialization = KotlinXSerializationDeps()
}

class KotlinXCoroutinesDeps internal constructor() {
    val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    val jvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    val js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.kotlinxCoroutines}"
    val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"
}

class KinjectDeps internal constructor() {
    val common = "com.rallyhealth.kinject:kinject:${Versions.kinject}"
    val jvm = "com.rallyhealth.kinject:kinject-jvm:${Versions.kinject}"
}

class KotlinXSerializationDeps internal constructor() {
    val common = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    val jvm = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
}

class KtorDeps internal constructor() {
    val client = KtorClientDeps()
    val plugin = KtorPluginDeps()
}

class KtorClientDeps internal constructor() {
    val core = KtorClientCoreDeps()
    val common = "io.ktor:ktor-client:${Versions.ktor}"
    val jvm = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    val darwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
}

class KtorClientCoreDeps internal constructor() {
    val common = "io.ktor:ktor-client-core:${Versions.ktor}"
    val jvm = "io.ktor:ktor-client-jvm:${Versions.ktor}"
}

class KtorPluginDeps internal constructor() {
    val json = KtorPluginJsonDeps()
    val serialization = KtorPluginSeralizationDeps()
    val logging = KtorPluginLoggingDeps()
    val auth = KtorFeatureAuthDeps()
}

class KtorPluginJsonDeps internal constructor() {
    val jvm = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
    val darwin = "io.ktor:ktor-client-json-darwin:${Versions.ktor}"
    val common = "io.ktor:ktor-client-json:${Versions.ktor}"
}

class KtorPluginSeralizationDeps internal constructor() {
    val common = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    val jvm = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
    val native = "io.ktor:ktor-client-serialization-native:${Versions.ktor}"
    val content = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
}

class KtorPluginLoggingDeps internal constructor() {
    val common = "io.ktor:ktor-client-logging:${Versions.ktor}"
    val jvm = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
    val native = "io.ktor:ktor-client-logging-native:${Versions.ktor}"
}

class KtorFeatureAuthDeps internal constructor() {
    val common = "io.ktor:ktor-client-auth:${Versions.ktor}"
    val jvm = "io.ktor:ktor-client-auth-jvm:${Versions.ktor}"
    val native = "io.ktor:ktor-client-auth-native:${Versions.ktor}"
}

class CoilDeps internal constructor() {
    val coilCore = "io.coil-kt:coil:${Versions.coil}"
    val coilSvg = "io.coil-kt:coil-svg:${Versions.coil}"
    val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
}