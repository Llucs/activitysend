pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io") // pode ficar aqui também
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "ActivitySend"

// Garante que o SDK do Android seja encontrado corretamente
gradle.rootProject {
    val sdkDir = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
    if (sdkDir != null) {
        println("✅ Android SDK found at: $sdkDir")
    } else {
        println("⚠️ Android SDK not found — check ANDROID_HOME/ANDROID_SDK_ROOT")
    }
}

include(":app")