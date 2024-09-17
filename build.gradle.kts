// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.hilt.plugin) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.room.compiler) apply false

  alias(libs.plugins.jlleitschuh) apply true
  alias(libs.plugins.detekt) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.module.graph) apply true
}
subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  ktlint {
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
  }

  tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask>().configureEach {
  }
}

subprojects {
  apply(plugin = "io.gitlab.arturbosch.detekt")
  detekt {
    buildUponDefaultConfig = true
    config = files("${project.rootDir}/config/detekt/detekt.yml")
    parallel = true
  }
}
