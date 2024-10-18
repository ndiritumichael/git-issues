import io.sentry.android.gradle.extensions.InstrumentationFeature
import io.sentry.android.gradle.instrumentation.logcat.LogcatLevel
import java.util.Properties

val keystoreFile = project.rootProject.file("local.properties")
val properties = Properties()
properties.load(keystoreFile.inputStream())

val sentryDsn = properties.getProperty("SENTRYDSN") ?: ""
val githubClientKey = properties.getProperty("GITHUBCLIENTKEY") ?: ""
val githubSecret = properties.getProperty("GITHUBSECRET") ?: ""
val sentryAuthToken = properties.getProperty("SENTRYAUTHTOKEN") ?: ""

plugins {
    alias(libs.plugins.gitissuesmobile.application.compose)
    alias(libs.plugins.gitissuesmobile.android.hilt)

    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlinX.serialization)
    alias(libs.plugins.sentry.kotlin.compiler)
    alias(libs.plugins.sentry.android)

    alias(libs.plugins.gitissuesmobile.android.application.jacoco)
}

android {
    namespace = "com.devmike.gitissuesmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devmike.gitissuesmobile"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "GITHUBCLIENTKEY", "\"$githubClientKey\"")
        buildConfigField("String", "GITHUBSECRET", "\"$githubSecret\"")
        buildConfigField("String", "SENTRYDSN", "\"$sentryDsn\"")

        testInstrumentationRunner = "com.devmike.gitissuesmobile.CustomTestRunner"
    }

    testOptions {
        packaging {
            jniLibs {
                useLegacyPackaging = true
            }
            resources.excludes.add("META-INF/*")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}
dependencies {
    implementation(projects.feature.repository)
    implementation(projects.feature.issues)
    implementation(libs.appauth)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.core.splashscreen)

    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)

    androidTestImplementation(projects.core.testing)
}

sentry {

    org.set("michael-ndiritu")

    projectName.set("gitissues")

    authToken.set("\"$sentryAuthToken\"")

    autoUploadProguardMapping.set(true)

    dexguardEnabled.set(false)

    uploadNativeSymbols.set(false)

    autoUploadNativeSymbols.set(true)

    includeNativeSources.set(false)

    includeSourceContext.set(false)

    tracingInstrumentation {
        enabled.set(true)

        features.set(
            setOf(
                InstrumentationFeature.DATABASE,
                InstrumentationFeature.FILE_IO,
                InstrumentationFeature.OKHTTP,
                InstrumentationFeature.COMPOSE,
            ),
        )

        logcat {
            enabled.set(true)

            minLevel.set(LogcatLevel.WARNING)
        }

        excludes.set(emptySet())
    }

    autoInstallation {
        enabled.set(true)

        sentryVersion.set("7.14.0")
    }

    includeDependenciesReport.set(true)

    telemetry.set(true)
}
