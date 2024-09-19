plugins {
    alias(libs.plugins.gitissuesmobile.android.library)
    alias(libs.plugins.room.compiler)
    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.devmike.database"
    room {

        schemaDirectory("$projectDir/schemas")
    }
}
dependencies {
    api(libs.room.runtime)
    api(libs.room.ktx)
    implementation(libs.room.testing)
    implementation(libs.androidx.test.ext)
    ksp(libs.room.compiler)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)

    api(libs.androidx.paging.runtime)

    implementation(project(":core:network"))
}
