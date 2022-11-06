import jm.droid.compile.setupLibraryModule

plugins {
    id("jm.droid.library")
}

setupLibraryModule(buildConfig = true, publish = true)
dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.android.ext)
    androidTestImplementation(libs.test.android.espresso)
}