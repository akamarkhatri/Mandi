
buildscript {
    extra.apply {
        set("compose_ui_version","1.1.1")
    }

    repositories {
        google()
        maven { setUrl("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.android)
        classpath(Plugins.kotlin)
        classpath(Plugins.kotlinSerialization)
        classpath(Plugins.hilt)
        classpath(Plugins.navArgument)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
