plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.tuempresa.controlparental"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tuempresa.controlparental"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
}
