plugins {
    kotlin("multiplatform") version "2.3.10"
    kotlin("plugin.serialization") version "2.3.10"
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


group = "dev.voir"
version = "1.0.7"

kotlin {
    jvmToolchain(21)

    jvm()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")

            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-core:3.4.1")
            implementation("io.ktor:ktor-client-content-negotiation:3.4.1")
            implementation("io.ktor:ktor-client-serialization:3.4.1")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.1")
        }

        commonTest.dependencies {
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            implementation(kotlin("stdlib-jdk8"))

            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-okhttp:3.4.1")
        }

        iosMain.dependencies {
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-core
            implementation("io.ktor:ktor-client-darwin:3.4.1")
        }
    }
}

afterEvaluate {
    configure<PublishingExtension> {
        publications.all {
            val mavenPublication = this as? MavenPublication
            mavenPublication?.artifactId =
                "${project.name}${"-$name".takeUnless { "kotlinMultiplatform" in name }.orEmpty()}"
        }
    }
}

publishing {
    if (project.hasProperty("sonatypeUsername") && project.hasProperty("sonatypePassword")) {
        repositories {
            maven {
                name = "Sonatype"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.property("sonatypeUsername").toString()
                    password = project.property("sonatypePassword").toString()
                }
            }
        }
    }

    publications.withType<MavenPublication> {
        artifactId = "exchangeit-sdk"
        groupId = "dev.voir"
        version = "1.0.7"

        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this.name)
        })

        pom {
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
}

signing {
    if (project.hasProperty("signing.gnupg.keyName") && project.hasProperty("signing.gnupg.passphrase")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
