import org.jetbrains.kotlin.konan.properties.Properties
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "kr.co.korean.network"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        /**
         * 실무 진행시엔 '.gitignore'에 'local.properties'를 포함하지 않는 스크립트를 작성합니다.
         */
        buildConfigField("String", "marbleApiUrl", "${properties["marbleApiUrl"]}")
        buildConfigField("String", "marbleHash", "${properties["marbleHash"]}")
        buildConfigField("String", "marblePubKey", "${properties["marblePubKey"]}")
        buildConfigField("String", "marblePrivKey", "${properties["marblePrivKey"]}")

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":common"))

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.kotlinx.serialization.json)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}