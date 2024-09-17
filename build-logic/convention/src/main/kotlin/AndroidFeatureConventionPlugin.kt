

import com.android.build.gradle.LibraryExtension
import com.devmike.gitissuesmobile.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("gitissuesmobile.android.library")
        apply("gitissuesmobile.android.library.compose")
        apply("gitissuesmobile.android.hilt")
      }
      extensions.configure<LibraryExtension> {
        defaultConfig {
          testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        }
        testOptions.animationsDisabled = true
      }

      dependencies {
        add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
        add(
          "implementation",
          libs.findLibrary("androidx.lifecycle.viewmodel.compose").get(),
        )
      }
    }
  }
}
