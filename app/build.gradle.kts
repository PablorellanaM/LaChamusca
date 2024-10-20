plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services") // Plugin de Google Services para Firebase
}

android {
    namespace = "com.example.lachamusca"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lachamusca"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11 // Cambiar a Java 11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11" // Cambiar a Java 11
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
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.7.2")

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")

    // Material Design 3
    implementation("androidx.compose.material3:material3:1.1.0")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.0.0")

    // Firebase BOM (Bill of Materials) para Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")

    // Google Places API
    implementation("com.google.android.libraries.places:places:2.6.0")

    // Google Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // Google Maps Compose
    implementation("com.google.maps.android:maps-compose:2.2.0")

    // Google Play Services Location (para obtener la ubicación)
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Excluir clases duplicadas de android.support y appcompat
    configurations.all {
        exclude(group = "android.support", module = "support-v4")
    }

    // Excluir clases duplicadas de appcompat-resources y appcompat
    implementation("androidx.appcompat:appcompat:1.4.1") {
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }
    implementation("androidx.appcompat:appcompat-resources:1.4.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.0")
}
