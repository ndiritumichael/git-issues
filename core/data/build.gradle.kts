plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.gitissuesmobile.android.library.jacoco)
}

android {
    namespace = "com.devmike.data"
}

dependencies {

    api(projects.core.domain)
    api(projects.core.network)
    api(projects.core.database)
    testImplementation(libs.androidx.test.ext)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.truth)
    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.robolectric)
}
