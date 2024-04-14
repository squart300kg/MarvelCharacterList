plugins {
    alias(libs.plugins.architecture.sample.library.base.setting)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "kr.co.architecture.work"

    defaultConfig {
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":common"))

    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)
    implementation(libs.appcompat)

    kapt(libs.com.google.dagger.hilt.compiler)

    testImplementation(libs.junit)
}