plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)

    alias(libs.plugins.gitissuesmobile.android.library.jacoco)
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

// kover {
//    reports {
//        // filters for all report types of all build variants
//        filters.excludes.androidGeneratedClasses()
//        filters {
//        }
//
//        filters {
//            excludes {
//                classes(
//                    // excludes debug classes
//                    "*.DebugUtil",
//                    "*Generated",
//                    "*CustomAnnotationToExclude",
//                    "*.di",
//                )
//            }
//        }
//
//        variant("release") {
//            // verification only for 'release' build variant
//            verify.rule {
//                minBound(50)
//            }
//
//            // filters for all report types only for 'release' build variant
//            filters.excludes {
//                androidGeneratedClasses()
//                classes(
//                    // excludes debug classes
//                    "*.DebugUtil",
//                )
//            }
//        }
//    }
// }
