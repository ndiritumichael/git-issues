plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
}

android {
    namespace = "com.devmike.testing"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(libs.androidx.arch.core.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.core.ktx)
    implementation(libs.truth)
    implementation(libs.robolectric)
    implementation(libs.mockk)
    // For Robolectric tests.
    implementation(libs.hilt.android.testing)
    ksp(libs.hilt.compiler)
    implementation(libs.datastore)
}
