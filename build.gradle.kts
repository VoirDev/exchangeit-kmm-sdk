plugins {
    kotlin("multiplatform") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
    id("org.jetbrains.dokka") version "1.9.10"
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

group = "dev.voir"
version = "1.0.0"

val GITHUB_USER: String by project
val GITHUB_TOKEN: String by project

kotlin {
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-core:2.3.7")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
            implementation("io.ktor:ktor-client-serialization:2.3.7")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
        }
        commonTest.dependencies {
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            implementation(kotlin("stdlib-jdk8"))

            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-okhttp:2.3.7")
        }

        iosMain.dependencies {
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-darwin:2.3.7")
        }
    }
}

publishing {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/voirdev/exchangeit-kmm-sdk")
            credentials {
                username = GITHUB_USER
                password = GITHUB_TOKEN
            }
        }
    }
}