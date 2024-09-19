plugins {
    alias(libs.plugins.gitissuesmobile.android.feature)
    alias(libs.plugins.gitissuesmobile.library.compose)
}

android {
    namespace = "com.devmike.repository"
}

dependencies {

    implementation(project(":core:data"))

    implementation(libs.androidx.paging.compose)
}
