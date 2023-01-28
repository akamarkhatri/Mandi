plugins {
    id ("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.mandi"
    compileSdk = AndroidConfig.compileSdkVersion
    defaultConfig {
        applicationId = "com.mandi"
        minSdk = AndroidConfig.minSdkVersion
        targetSdk = AndroidConfig.minSdkVersion
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        resources.excludes += "META-INF/*.kotlin_module"
    }
}
val compose_ui_version = "1.1.1"
//    rootProject.extra.get("compose_ui_version") as String
dependencies {
    implementation(Deps.androidx.appcompat)
//    implementation("androidx.activity:activity-compose:1.6.1")
    implementation(Deps.androidx.composeActivity)
    implementation(platform(Deps.androidx.composeBom))
    for (lib in Deps.androidx.compose) {
        implementation(lib)
    }
    implementation(Deps.androidx.constraintLayoutCompose)
    implementation(Deps.androidx.core)
    implementation(Deps.androidx.lifecycleViewModelKtx)
    implementation(Deps.androidx.lifecycleViewModelCompose)
    implementation(Deps.androidx.test.espressoIdlingResources)
    implementation(Deps.coil.coilCore)
    implementation(Deps.coil.coilSvg)
    implementation(Deps.coil.coilCompose)
    implementation(Deps.kotlinx.coroutines.android)
    implementation(Deps.kotlinx.serialization.jvm)
    implementation(Deps.ktor.plugin.json.jvm)

    for (lib in Deps.androidx.composeDebug) {
        debugImplementation(lib)
    }

    for (lib in Deps.androidx.test.all) {
        androidTestImplementation(lib)
    }
    for (lib in Deps.androidx.test.espresso) {
        androidTestImplementation(lib)
    }
    androidTestImplementation(platform(Deps.androidx.composeBom))
    androidTestImplementation(Deps.androidx.test.composeTest)

    androidTestImplementation(Deps.okhttp)
    androidTestImplementation(Deps.ktor.client.jvm)
    androidTestImplementation(Deps.ktor.plugin.json.jvm)
    androidTestImplementation(Deps.ktor.plugin.logging.jvm)
    androidTestImplementation(Deps.ktor.plugin.serialization.common)
    androidTestImplementation(Deps.ktor.plugin.serialization.content)
    androidTestImplementation(Deps.ktor.plugin.serialization.jvm)
    androidTestUtil(Deps.androidx.test.orchestrator)

    /*implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:$compose_ui_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation("androidx.compose.material:material:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")*/
}

