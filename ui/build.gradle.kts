@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "kr.co.korean.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

//    implementation(project(":app"))
    implementation(project(":domain"))
    implementation(project(":data:repository"))
    implementation(project(":data:work"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.io.coil.kt)
    implementation(libs.com.google.dagger.hilt.android)
    implementation(libs.androidx.paging.runtimne)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.work.runtime)
    kapt(libs.com.google.dagger.hilt.compiler)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
}