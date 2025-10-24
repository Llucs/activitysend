plugins {
    id("com.android.application") version "8.1.0"
    id("org.jetbrains.kotlin.android") version "1.9.25"
}

android {
    namespace = "com.llucs.activitysend"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.llucs.activitysend"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Core e utilitários
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Compose UI
    implementation("androidx.compose.ui:ui:1.5.15")
    implementation("androidx.compose.animation:animation:1.5.15")
    implementation("androidx.compose.animation:animation-core:1.5.15")
    implementation("androidx.compose.animation:animation-graphics:1.5.15")

    // Material 3 Expressive e ícones
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.15")

    // Lifecycle e navegação
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("androidx.navigation:navigation-compose:2.8.0-beta02")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.1.1")

    // Coil para imagens
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Shizuku atualizado
    implementation("dev.rikka.shizuku:api:13.2.0")
    implementation("dev.rikka.shizuku:provider:13.2.0")
}