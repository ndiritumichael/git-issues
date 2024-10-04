plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
}

android {
    namespace = "com.devmike.testing"
}

dependencies {

    implementation(libs.androidx.arch.core.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.core.ktx)
    implementation(libs.truth)
    implementation(libs.robolectric)
    implementation(libs.mockk)
}
