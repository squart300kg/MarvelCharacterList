@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "kr.co.korean.repository"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

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

    implementation(project(":data:database"))
    implementation(project(":data:network"))
    implementation(project(":data:work"))
    implementation(project(":common"))

    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.runtimne)

    kapt(libs.com.google.dagger.hilt.compiler)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    testImplementation(libs.androidx.test.ext.junit)
}