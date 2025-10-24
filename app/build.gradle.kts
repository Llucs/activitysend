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
        kotlinCompilerExtensionVersion = "1.5.+"
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
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.9.0"))

    // Compose Core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.compiler:compiler:1.5.+") // sempre pega último patch 1.5.x

    // Android Core e Activity
    implementation("androidx.core:core-ktx:1.13.+")
    implementation("androidx.activity:activity-compose:1.9.+")

    // Navigation e Lifecycle
    implementation("androidx.navigation:navigation-compose:2.8.+")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.+")

    // Accompanist
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.+") {
        exclude(group = "androidx.compose.compiler", module = "compiler")
    }

    // SplashScreen e Coil
    implementation("androidx.core:core-splashscreen:1.0.+")
    implementation("io.coil-kt:coil-compose:2.+")

    // Shizuku
    implementation("dev.rikka.shizuku:api:13.+")
    implementation("dev.rikka.shizuku:provider:13.+")
}