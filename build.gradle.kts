plugins {
    kotlin("multiplatform") version "1.3.21"
}

repositories {
    jcenter()
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("stdlib"))
            }
        }
    }
}