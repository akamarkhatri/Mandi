plugins {
    id ("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlinx-serialization")
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
        testInstrumentationRunner = "com.mandi.base.UITestRunner"
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
    }
}
dependencies {
    implementation(Deps.androidx.appcompat)
    implementation(Deps.androidx.composeActivity)
    implementation(platform(Deps.androidx.composeBom))
    for (lib in Deps.androidx.compose) {
        implementation(lib)
    }
    implementation(Deps.androidx.constraintLayoutCompose)
    implementation(Deps.androidx.core)
    implementation(Deps.androidx.lifeCycleRuntimeCompose)
    implementation(Deps.androidx.lifeCycleRuntimeComposektx)
    implementation(Deps.androidx.lifecycleViewModelKtx)
    implementation(Deps.androidx.lifecycleViewModelCompose)
    implementation(Deps.androidx.test.espressoIdlingResources)
    implementation(Deps.coil.coilCore)
    implementation(Deps.coil.coilSvg)
    implementation(Deps.coil.coilCompose)
    implementation(Deps.kotlinx.coroutines.android)
    implementation(Deps.kotlinx.serialization.common)
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

    implementation(Deps.android.hiltAndroid)
    kapt(Deps.android.hiltCompiler)
    kaptTest(Deps.android.hiltCompiler)
    for (lib in Deps.androidx.hiltAndroidXNavigationCompose) {
        implementation(lib)
    }
    androidTestImplementation(platform(Deps.androidx.composeBom))
    androidTestImplementation(Deps.androidx.test.composeTest)
    androidTestUtil(Deps.androidx.test.orchestrator)
    kaptAndroidTest(Deps.android.hiltCompiler)
    androidTestImplementation(Deps.android.hiltTesting)

    testImplementation(Deps.junit)
    testImplementation(Deps.kotlinx.coroutines.test)
    for (lib in Deps.androidx.test.all) {
        testImplementation(lib)
    }
    testImplementation(Deps.androidx.test.turbine)
}

