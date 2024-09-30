plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.devmike.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.database)
}
