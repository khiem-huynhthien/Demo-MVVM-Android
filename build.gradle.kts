// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    dependencies {
        classpath(libs.hilt.plugin)
        classpath("org.jetbrains.kotlinx:kover-gradle-plugin:0.8.3")
    }
}