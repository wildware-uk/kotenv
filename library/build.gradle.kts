plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("module.publication")
    id("dev.mokkery") version "2.3.0"
}

group = "dev.wildware"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    linuxX64()
    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.okio)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.okio.fakefilesystem)
            }
        }
    }
}
