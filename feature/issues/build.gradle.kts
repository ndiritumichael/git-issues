plugins {
    alias(libs.plugins.gitissuesmobile.android.feature)
    alias(libs.plugins.gitissuesmobile.library.compose)
}

android {
    namespace = "com.devmike.issues"
}
dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
}
