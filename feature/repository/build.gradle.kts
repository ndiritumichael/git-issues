plugins {
    alias(libs.plugins.gitissuesmobile.android.feature)
    alias(libs.plugins.gitissuesmobile.library.compose)
    alias(libs.plugins.gitissuesmobile.android.library.jacoco)
}

android {
    namespace = "com.devmike.repository"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.domain)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.truth)
    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.robolectric)
}
