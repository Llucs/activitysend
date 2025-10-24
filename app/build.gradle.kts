plugins {
    id("com.android.application") version "8.6.0"
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
        kotlinCompilerExtensionVersion = "1.6.10"
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
    // Compose BOM gerencia as versões automaticamente
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))

    // UI principal
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Material 3 Expressive
    implementation("androidx.compose.material3:material3")

    // Animações
    implementation("androidx.compose.animation:animation")

    // Ícones
    implementation("androidx.compose.material:material-icons-extended")

    // Core e Activity
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Navegação e Lifecycle
    implementation("androidx.navigation:navigation-compose:2.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

    // Accompanist (animações de navegação)
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Coil (imagens)
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Shizuku
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")
}