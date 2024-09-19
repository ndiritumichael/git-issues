plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
}

android {
    namespace = "com.devmike.datastore"
}

dependencies {
    implementation(libs.datastore)
}
