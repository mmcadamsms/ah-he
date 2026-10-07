rootProject.name = "ah-he"

// Client applications
include(":clients:web")
include(":clients:android")
include(":clients:ios")

// Backend runtimes
include(":backend:web-api")
include(":backend:background-worker")

// Cross-platform shared code
include(":common:kotlin")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
