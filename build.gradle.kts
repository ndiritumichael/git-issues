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
    // id("com.vanniktech.android.junit.jacoco") version "0.16.0"

    // id("org.jetbrains.kotlinx.kover") version "0.8.3"

    // id("jacoco")
    // id("jacoco-report-aggregation")
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    //   apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "jacoco")

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
}

buildscript {
    val kotlinVersion by extra("1.4.10")
    val jacocoVersion by extra("0.2")

    dependencies {
        classpath("com.dicedmelon.gradle:jacoco-android:0.1.5")

        classpath("com.hiya:jacoco-android:$jacocoVersion")
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "jacoco")
    detekt {
        buildUponDefaultConfig = true

        config.setFrom("${project.rootDir}/config/detekt/detekt.yml")
        parallel = true
    }
}

// tasks.withType(Test) {
//    jacoco.includeNoLocationClasses = true
// }
