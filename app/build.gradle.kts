import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.superbeta.blibberly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.superbeta.blibberly"
        minSdk = 25
        targetSdk = 34
        versionCode = 5
        versionName = "0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "secret.properties")
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }


//    println(localProperties)
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField(
                "String",
                "SUPABASE_DEBUG_URL",
                localProperties.getProperty("SUPABASE_DEBUG_URL")
            )
            buildConfigField(
                "String",
                "SUPABASE_DEBUG_KEY",
                localProperties.getProperty("SUPABASE_DEBUG_KEY")
            )
        }

        debug {
            buildConfigField(
                "String",
                "SUPABASE_DEBUG_URL",
                localProperties.getProperty("SUPABASE_DEBUG_URL")
            )
            buildConfigField(
                "String",
                "SUPABASE_DEBUG_KEY",
                localProperties.getProperty("SUPABASE_DEBUG_KEY")
            )
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
//        resValues = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":chat"))
    implementation(libs.androidx.compose.material)
    implementation(project(":auth"))
    implementation(project(":profile_ops"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //FCM
//    implementation(libs.firebase.messaging)

    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)
    //compose animation
    implementation(libs.androidx.animation)

    //navigation
    implementation(libs.navigation.compose)

    //ktor client for supabase
    implementation(libs.ktor.client.android)

    //supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:compose-auth")
    implementation("io.github.jan-tennert.supabase:storage-kt")

    //gson
    implementation(libs.gson)

    //coil
    implementation(libs.coil.kt)

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //fonts
    implementation(libs.androidx.ui.text.google.fonts)

    //lottie
    implementation(libs.lottie.compose)
    //socket io
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //shimmer effect
    implementation(libs.compose.shimmer)

    //koin
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Firebase crashlytics
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}