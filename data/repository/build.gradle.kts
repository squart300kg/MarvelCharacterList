plugins {
    alias(libs.plugins.architecture.sample.library.base.setting)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "kr.co.architecture.repository"

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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":data:database"))
    implementation(project(":data:network"))
    implementation(project(":data:work"))
    implementation(project(":common"))

    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.runtimne)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)

    kapt(libs.com.google.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext.junit)
}