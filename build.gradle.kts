plugins {
    kotlin("multiplatform") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
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

group = "dev.voir.anyexchange"
version = "1.0.0"


kotlin {
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0-RC")

                // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
                implementation("io.ktor:ktor-client-core:2.3.3")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
                implementation("io.ktor:ktor-client-serialization:2.3.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
                implementation("io.ktor:ktor-client-logging:2.3.3")

                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }

        val commonTest by getting {
            dependencies {
                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
                implementation("io.ktor:ktor-client-okhttp:2.3.3")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
                implementation("io.ktor:ktor-client-darwin:2.3.3")
            }
        }

        val iosTest by creating {
            dependencies {
                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }

        sourceSets.filter { sourceSet ->
            sourceSet.name.run {
                startsWith("iosX64") ||
                        startsWith("iosArm") ||
                        startsWith("iosSimulator")
            }
        }.forEach { sourceSet ->
            if (sourceSet.name.endsWith("Main")) {
                sourceSet.dependsOn(iosMain)
            } else {
                sourceSet.dependsOn(iosTest)
            }
        }
    }
}
