import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.services)
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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(project(":auth"))
    implementation(project(":profile_ops"))
//    implementation(project(":blibberly_supabase"))
    //profile likes, dislikes, reports and matches
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //FCM
    implementation(libs.firebase.messaging)

    //compose
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    //material3
    implementation("androidx.compose.material3:material3")

    //preview
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //navigation
    implementation(libs.navigation.compose)

    //socket io
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }

    //gson
    implementation("com.google.code.gson:gson:2.11.0")

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //ktor client for supabase
    implementation("io.ktor:ktor-client-android:2.3.11")

    //supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:compose-auth")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //koin
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    //coil
    implementation(libs.coil.kt)

}