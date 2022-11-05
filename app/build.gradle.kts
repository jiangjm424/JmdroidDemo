import jm.droid.compile.setupAppModule

plugins {
    id("jm.droid.application")
}
setupAppModule {
    defaultConfig {
        applicationId = "jm.example.droid.demo"
    }
    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
//        val benchmark by creating {
//            // Enable all the optimizations from release build through initWith(release).
//            initWith(release)
//            matchingFallbacks.add("release")
//            // Debug key signing is available to everyone.
//            signingConfig = signingConfigs.getByName("debug")
//            // Only use benchmark proguard rules
//            proguardFiles("benchmark-rules.pro")
//            //  FIXME enabling minification breaks access to demo backend.
//            isMinifyEnabled = false
//            applicationIdSuffix = ".benchmark"
//        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.viewmodel)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.android.ext)
    androidTestImplementation(libs.test.android.espresso)
}