plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.mint"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mint"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.compose.foundation:foundation:1.4.2")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.2")           // 예시 버전
    implementation("androidx.compose.material:material:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.2")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Accompanist Pager
    implementation ("com.google.accompanist:accompanist-pager:0.31.3-beta")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.31.3-beta")


    // 기타 필요한 라이브러리들(예: Coil로 이미지 로딩, 등등)
    // implementation "io.coil-kt:coil-compose:2.2.2"
}