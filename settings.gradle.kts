rootProject.name = "ah-he"

// Backend services
include(":services:web-ux")
include(":services:web-api")
include(":services:background-worker")

// Mobile & shared
include(":androidApp")
include(":iosApp")
include(":shared")

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
