// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.android.library) apply false
}