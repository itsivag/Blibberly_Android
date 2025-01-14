import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.superbeta.blibberly_auth"
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
            buildConfigField(
                "String",
                "WEB_GOOGLE_CLIENT_ID",
                localProperties.getProperty("WEB_GOOGLE_CLIENT_ID")
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
            buildConfigField(
                "String",
                "WEB_GOOGLE_CLIENT_ID",
                localProperties.getProperty("WEB_GOOGLE_CLIENT_ID")
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
//    implementation(project(":blibberly_supabase"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.runtime.compose)

    //ktor client for supabase
    implementation(libs.ktor.client.android)

    //supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:compose-auth")
    implementation("io.github.jan-tennert.supabase:storage-kt")

    //compose
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    //material3
    implementation(libs.androidx.compose.material3.material3)

    //preview
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)

    //navigation
    implementation(libs.navigation.compose)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //gson
    implementation(libs.gson)

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //koin
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    //firebase auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.play.services.auth)

    //cred manager
    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
//    implementation (libs.androidx.credentials)
//    implementation (libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
//    implementation ("androidx.credentials:credentials:<latest version>")
//    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
//    implementation ("com.google.android.libraries.identity.googleid:googleid:<latest version>")
}