plugins {
    alias(libs.plugins.gitissuesmobile.android.feature)
    alias(libs.plugins.gitissuesmobile.library.compose)
}

android {
    namespace = "com.devmike.issues"
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    implementation(projects.core.domain)
}
