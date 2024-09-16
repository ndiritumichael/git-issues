package com.devmike.gitissuesmobile

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("testImplementation", platform(bom))
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
            add("implementation", libs.findBundle("compose").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            add("testImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
        }
    }
}
