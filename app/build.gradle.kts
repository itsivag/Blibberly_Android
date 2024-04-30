plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.superbeta.blibberly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.superbeta.blibberly"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //compose animation
    implementation("androidx.compose.animation:animation:1.6.6")
    //navigation
    implementation(libs.navigation.compose)

    //supabase
    implementation("io.github.jan-tennert.supabase:compose-auth:2.3.0")
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.3.0")
    implementation("io.github.jan-tennert.supabase:storage-kt:2.3.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.3.0")

    //gson
    implementation("com.google.code.gson:gson:2.10.1")

    //coil
    implementation(libs.coil.kt)

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //stream
    val stream_version = "6.3.1"
    implementation ("io.getstream:stream-chat-android-compose:$stream_version")
    implementation ("io.getstream:stream-chat-android-client:$stream_version")
    implementation ("io.getstream:stream-chat-android-state:$stream_version")
    implementation ("io.getstream:stream-chat-android-offline:$stream_version")

    //creds manager
    implementation ("androidx.credentials:credentials:1.1.0")
    implementation ("androidx.credentials:credentials-play-services-auth:1.1.0")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    //fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.1")
}