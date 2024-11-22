// Aplicación: build.gradle.kts

// Plugins necesarios para la aplicación
plugins {
    alias(libs.plugins.android.application) // Plugin para aplicaciones Android
    alias(libs.plugins.jetbrains.kotlin.android) // Plugin para desarrollo en Kotlin
    id("com.google.gms.google-services") // Plugin de Google Services para integración con Firebase
}

android {
    // Configuración de la firma (signing) en modo debug
    signingConfigs {
        getByName("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore") // Archivo de la clave
            storePassword = "android" // Contraseña de la clave
            keyAlias = "androiddebugkey" // Alias de la clave
            keyPassword = "android" // Contraseña del alias
        }
    }

    namespace = "com.example.lachamusca" // Espacio de nombres de la aplicación
    compileSdk = 34 // Versión del SDK de compilación

    defaultConfig {
        applicationId = "com.example.lachamusca" // ID único de la aplicación
        minSdk = 26 // Versión mínima del SDK requerido
        targetSdk = 34 // Versión del SDK objetivo
        versionCode = 1 // Versión del código de la app
        versionName = "1.0" // Versión de lanzamiento

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Configuración de pruebas
        vectorDrawables {
            useSupportLibrary = true // Soporte para vector drawables en versiones antiguas
        }
    }

    // Tipos de compilación
    buildTypes {
        release {
            isMinifyEnabled = false // Desactiva la minificación en la versión de lanzamiento
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Configuración ProGuard para optimización de código
            )
        }
    }

    // Configuración de opciones de compatibilidad de Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Opciones de Kotlin
    kotlinOptions {
        jvmTarget = "11" // Configuración de la versión de la JVM
    }

    // Habilitar características de Jetpack Compose
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Versión del compilador de Compose
    }

    // Configuración de exclusión de recursos innecesarios
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Excluir archivos de licencias
        }
    }
}

dependencies {
    // Dependencias fundamentales
    implementation("androidx.core:core-ktx:1.10.1") // Extensiones de Kotlin para Android
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0") // Gestión de ciclos de vida
    implementation("androidx.activity:activity-compose:1.7.2") // Soporte para Jetpack Compose en actividades

    // Dependencias de Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")

    // Material Design 3
    implementation("androidx.compose.material3:material3:1.1.0")

    // Navegación en Compose
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Carga de imágenes con Coil
    implementation("io.coil-kt:coil-compose:2.0.0")

    // Firebase BOM para sincronizar versiones de Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase Authentication y Firestore
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // API de Lugares de Google
    implementation("com.google.android.libraries.places:places:2.6.0")

    // Google Maps SDK y soporte en Compose
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.maps.android:maps-compose:2.2.0")

    // Servicios de ubicación de Google Play
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Configuración para excluir clases duplicadas
    configurations.all {
        exclude(group = "android.support", module = "support-v4")
    }
    implementation("androidx.appcompat:appcompat:1.4.1") {
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }
    implementation("androidx.appcompat:appcompat-resources:1.4.1")

    // Dependencias para pruebas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.0")
}

