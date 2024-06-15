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
    implementation(project(":blibberly_chat"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material:1.6.7")
    implementation("androidx.compose.material:material-icons-extended")
    //compose animation
    implementation("androidx.compose.animation:animation:1.6.6")

    //navigation
    implementation(libs.navigation.compose)

    //ktor client for supabase
    implementation("io.ktor:ktor-client-android:2.3.11")

    //supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:compose-auth")

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

    //fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.1")

    //lottie
    implementation("com.airbnb.android:lottie-compose:6.4.1")
    //socket io
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }

    //
    dependencies {
        implementation("androidx.credentials:credentials:1.2.2")
        implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

        // optional - needed for credentials support from play services, for devices running
        // Android 13 and below.
        implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    }
// optional - needed for credentials support from play services, for devices running
// Android 13 and below.
    implementation("androidx.credentials:credentials-play-services-auth:<latest version>")

}