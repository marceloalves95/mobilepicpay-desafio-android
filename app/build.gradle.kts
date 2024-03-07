plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
android {
    namespace = "com.picpay.desafio.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.picpay.desafio.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.picasso)
    implementation(libs.gson)
    implementation(libs.circleimageview)

    //Material
    implementation(libs.material)

    //AndroidX
    implementation(libs.bundles.androidx)

    //Lifecycle
    implementation(libs.bundles.lifecycle)

    //ThirdParty
    implementation(libs.koin)
    implementation(libs.bundles.retrofit)

    //Unit Test
    testImplementation(libs.junit)
    testImplementation(libs.assertK)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)

    //Instrumental Test
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.assertK)
    androidTestImplementation(libs.test.core.ktx)
    androidTestImplementation(libs.arch.core.testing)
    androidTestImplementation(libs.barista)
}
