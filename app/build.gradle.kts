plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.human_developing_app.nasa_gallery"
  compileSdk = 34

  buildFeatures {
    buildConfig = true
  }

  defaultConfig {
    applicationId = "com.human_developing_app.nasa_gallery"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  viewBinding {
    enable = true
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.google.code.gson:gson:2.10.1")
  implementation("com.github.bumptech.glide:glide:4.16.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.facebook.shimmer:shimmer:0.5.0")
  implementation("androidx.core:core-splashscreen:1.0.1")
  implementation(files("libs/touchview-release.aar"))
  androidTestImplementation("androidx.test:rules:1.5.0")
  val navigationVersion = "2.7.6"
  implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
  implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
  implementation("com.google.dagger:hilt-android:2.50")
  kapt("com.google.dagger:hilt-compiler:2.50")
  debugImplementation("com.squareup.leakcanary:leakcanary-android:2.13")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
}

kapt {
  correctErrorTypes = true
}