plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.flow"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.flow"
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
        viewBinding = true
    }
}

dependencies {
    // 기존 Compose 관련은 유지
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    // Compose BOM (최신 버전으로 수정: 예시 2023.03.00)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    // Compose
    implementation("androidx.compose.ui:ui") // BOM이 있으면 버전 생략 가능
    implementation("androidx.compose.ui:ui-tooling-preview")
    // Material 3 (안정화 버전 예시)
    implementation("androidx.compose.material3:material3")

    // AndroidX (추가)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Fragment, Activity KTX
    implementation("androidx.fragment:fragment-ktx:1.5.5")
    implementation("androidx.activity:activity-ktx:1.6.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // ViewPager, TabLayout
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // CircleIndicator
    implementation("me.relex:circleindicator:2.1.6")

    // 기존 의존성들 추가
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("com.google.code.gson:gson:2.8.7") // GSON
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // RoomDB
    implementation("androidx.room:room-ktx:2.4.1")
    implementation("androidx.room:room-runtime:2.4.1")
    kapt ("androidx.room:room-compiler:2.5.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
}
