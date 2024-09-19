plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.devmike.data"
}

dependencies {

    api(project(":core:database"))
    implementation(project(":core:network"))
    api(project(":core:domain"))
}
