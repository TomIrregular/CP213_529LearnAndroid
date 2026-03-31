plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services") version "4.4.4" apply false
}

android {
    namespace = "com.example.a529lablearnandroid"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.a529lablearnandroid"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Retrofit สำหรับคุยกับ Server
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Converter สำหรับแปลง JSON เป็น Data Class (Gson)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Lifecycle & ViewModel สำหรับ Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))

    // Standard local testing
    testImplementation("junit:junit:4.13.2")

    // Add Robolectric to simulate Android locally
    testImplementation("org.robolectric:robolectric:4.11.1")

    // AndroidX testing libraries for the local test folder
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("androidx.test:core:1.5.0")
}