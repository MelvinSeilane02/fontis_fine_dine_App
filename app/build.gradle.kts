plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    //id("com.google.gms.google-services") apply false
    //id("com.google.gms.google-services") // ✅ Important for Firebase
    //alias(libs.plugins.kotlin.parcelize) // ✅ this activates @Parcelize support
}

apply(plugin = "kotlin-parcelize")
//apply (plugin= "com.google.gms.google-services")
//apply (plugin = "com.google.gms.google-services")

android {
    namespace = "com.example.fontis_fine_dine"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fontis_fine_dine"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //implementation(libs.appcompat)

    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    // ✅ Firebase BoM - central version management
    //implementation(platform("com.google.firebase:firebase-bom:${libs.versions.firebaseBom.get()}"))

    // main modules (BOM will supply versions)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-config")

    implementation("com.squareup.picasso:picasso:2.71828")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}