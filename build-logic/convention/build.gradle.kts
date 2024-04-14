import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "kr.co.architecture.build.logic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}


gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "architecture.sample.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidLibraryCompose") {
            id = "architecture.sample.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidApplicationBaseSetting") {
            id = "architecture.sample.application.base.setting"
            implementationClass = "AndroidApplicationBaseSettingPlugin"
        }
        register("androidLibrary") {
            id = "architecture.sample.library.base.setting"
            implementationClass = "AndroidLibraryBaseSettingPlugin"
        }
    }
}