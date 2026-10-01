plugins {
    java
}

allprojects {
    group = "com.ahhe"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
