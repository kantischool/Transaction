plugins {
    alias(libs.plugins.android.application)
  //  alias(libs.plugins.daggerHiltAndroid)
//    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.event.transactions"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.event.transactions"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    viewBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dagger Hilt
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.android.compiler)

    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)

    // Room DB
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Gson
    implementation(libs.gson)

    // Security
    implementation(libs.security)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
}