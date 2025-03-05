import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.services)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.superbeta.blibberly_chat"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "secret.properties")
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(project(":auth"))
    implementation(project(":profile_ops"))
    implementation(project(":models"))
    //    implementation(project(":blibberly_supabase"))
    //profile likes, dislikes, reports and matches
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //FCM
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)


    //compose
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    //material3
    implementation(libs.material3)

    //preview
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)

    //navigation
    implementation(libs.navigation.compose)

    //socket io
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }

    //gson
    implementation(libs.gson)

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //ktor client for supabase
    implementation(libs.ktor.client.android)

    //supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:compose-auth")

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //koin
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    //coil
    implementation(libs.coil.kt)

}