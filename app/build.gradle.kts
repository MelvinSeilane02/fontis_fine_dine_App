plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    //alias(libs.plugins.kotlin.parcelize) // ✅ this activates @Parcelize support
}

apply(plugin = "kotlin-parcelize")
apply (plugin= "com.google.gms.google-services")
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
    implementation(libs.appcompat)
    implementation(libs.firebase.database)
    // implementation(libs.firebase.firestore.ktx)
    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.0")
    // Use Firebase BoM (platform)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    // (optional but recommended)
    // Firebase libraries WITHOUT explicit versions (BOM controls versions)
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")          // if using auth
    implementation("com.google.firebase:firebase-analytics-ktx")      // optional

    implementation("com.google.firebase:firebase-storage:21.0.0")

    implementation("com.squareup.picasso:picasso:2.8")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}