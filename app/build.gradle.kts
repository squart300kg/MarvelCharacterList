plugins {
//    alias(libs.plugins.android.application)
    alias(libs.plugins.architecture.sample.application.base.setting)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.architecture.sample.application.compose)
    kotlin("kapt")
}

android {
    /**
     * 실제 앱 환경과 동일하게 jks파일로 패키징 및 빌드테스트를 진행하였습니다.
     */
    signingConfigs {
        create("release") {
            storeFile = file("../ssyssy.jks")
            storePassword = "ssyssy"
            keyAlias = "ssyssy"
            keyPassword = "ssyssy"
        }
    }
    namespace = "kr.co.architecture.app"

    defaultConfig {
        applicationId = "kr.co.architecture.ssy"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":ui:home"))
    implementation(project(":ui:bookmark"))
    implementation(project(":ui:base"))
    implementation(project(":domain"))
    implementation(project(":data:repository"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.junit)
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)
    kapt(libs.com.google.dagger.hilt.compiler)
}