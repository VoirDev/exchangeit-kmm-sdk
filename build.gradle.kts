plugins {
    kotlin("multiplatform") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
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
            name = "OSSRH"
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        
        maven {
            name = "github"
            setUrl("https://maven.pkg.github.com/VoirDev/exchangeit-kmm-sdk")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}