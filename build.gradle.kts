plugins {
    kotlin("multiplatform") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("maven-publish")
    id("signing")
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

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

group = "dev.voir"
version = "1.0.1"

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

        androidMain.dependencies {
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-okhttp:2.3.7")
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
    publications.withType<MavenPublication> {
        artifactId = "exchangeit-sdk"
        groupId = "dev.voir"
        version = "1.0.1"

        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this.name)
        })

        pom {
            packaging = "jar"
            name.set("Exchange It: Kotlin Multiplatform SDK")
            url.set("https://github.com/VoirDev/exchangeit-kmm-sdk/")
            description.set("SDK for Exchange It API written in Kotlin. For now supports iOS, JVM and Android.")

            licenses {
                license {
                    name.set("GNU Lesser General Public License, Version 3")
                    url.set("https://www.gnu.org/licenses/lgpl-3.0.txt")
                }
            }

            scm {
                connection.set("scm:https://github.com/VoirDev/exchangeit-kmm-sdk.git")
                developerConnection.set("scm:git@github.com:VoirDev/exchangeit-kmm-sdk.git")
                url.set("https://github.com/VoirDev/exchangeit-kmm-sdk/")
            }

            developers {
                developer {
                    id.set("checksanity")
                    name.set("Gary Bezruchko")
                    email.set("hello@exchangeit.app")
                    organization.set("VOIR")
                    organizationUrl.set("https://voir.dev")
                }
            }
        }
    }

    /*    repositories {
            maven {
                name = "Github"
                setUrl("https://maven.pkg.github.com/VoirDev/exchangeit-kmm-sdk")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }*/
}

signing {
    if (project.hasProperty("signing.gnupg.keyName") && project.hasProperty("signing.gnupg.passphrase")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
