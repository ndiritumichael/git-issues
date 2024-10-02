plugins {
    alias(libs.plugins.gitissuesmobile.android.library)
    alias(libs.plugins.kotlinX.serialization)
}

android {
    namespace = "com.devmike.domain"
    compileSdk = 34
}

dependencies {
    implementation(libs.androidx.paging.runtime)
    implementation(libs.kotlinx.serialization.json)
}
