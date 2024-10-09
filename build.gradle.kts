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
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.dokka)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    //  apply(plugin = "org.jetbrains.dokka")

    ktlint {
        android.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        filter {
            exclude("**/build/**")
        }
    }

    tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask>().configureEach {
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        buildUponDefaultConfig = true

        config.setFrom("${project.rootDir}/config/detekt/detekt.yml")
        parallel = true
    }
}
allprojects {
    apply(plugin = "org.jetbrains.dokka")
}

/**
 * fixes the error [Explicit Dependency](https://www.reddit.com/r/Kotlin/comments/18cjcbj/dokka_with_multilevel_multimodule_projects_using/)
 */
tasks.dokkaHtmlMultiModule {
    moduleName.set("GitIssues Dokka Documentation")
    dependsOn(":core:dokkaHtmlMultiModule")
    dependsOn(":feature:dokkaHtmlMultiModule")
}
