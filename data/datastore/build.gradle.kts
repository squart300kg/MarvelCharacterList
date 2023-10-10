@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "kr.co.korean.datastore"
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

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {

    implementation(project(":common"))

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}