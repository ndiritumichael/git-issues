plugins {
    alias(libs.plugins.gitissuesmobile.android.feature)
    alias(libs.plugins.gitissuesmobile.library.compose)
}

android {
    namespace = "com.devmike.repository"
}

dependencies {

    implementation(projects.core.data)

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    implementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.truth)
    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.robolectric)
}
