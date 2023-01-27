buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
   /* id 'com.android.application' version '7.3.0' apply false
    id 'com.android.library' version '7.3.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.6.10' apply false*/
    id("com.github.ben-manes.versions") version "0.43.0"
    id("nl.littlerobots.version-catalog-update") version "0.7.0"
}
apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")