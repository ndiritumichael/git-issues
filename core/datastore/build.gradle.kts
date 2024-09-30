plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
}

android {
    namespace = "com.devmike.datastore"
}

dependencies {
    implementation(libs.datastore)
    testImplementation(libs.androidx.test.ext)

    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.truth)
    testImplementation(libs.robolectric)
}
