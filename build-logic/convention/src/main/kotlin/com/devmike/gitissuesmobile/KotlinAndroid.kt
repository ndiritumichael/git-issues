package com.devmike.gitissuesmobile

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
  commonExtension.apply {
    compileSdk = 35

    defaultConfig {
      minSdk = 26
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
      add("implementation", libs.findLibrary("timber").get())
      add("implementation", libs.findLibrary("androidx.core.ktx").get())

      add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
      add("androidTestImplementation", libs.findLibrary("androidx.junit").get())

      add("testImplementation", libs.findLibrary("junit").get())
    }
  }
}
