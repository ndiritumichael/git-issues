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
    id("io.sentry.android.gradle") version ("4.11.0")
    id("io.sentry.kotlin.compiler.gradle") version ("4.11.0")

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
    // For Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.androidx.test.ext)

    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.truth)
    testImplementation(libs.robolectric)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)
    testImplementation(projects.core.testing)
    androidTestImplementation(projects.core.testing)
}

sentry {
    // Disables or enables debug log output, e.g. for for sentry-cli.
    // Default is disabled.
    debug.set(false)

    // The slug of the Sentry organization to use for uploading proguard mappings/source contexts.
    org.set("michael-ndiritu")

    // The slug of the Sentry project to use for uploading proguard mappings/source contexts.
    projectName.set("gitissues")

    // The authentication token to use for uploading proguard mappings/source contexts.
    // WARNING: Do not expose this token in your build.gradle files, but rather set an environment
    // variable and read it into this property.
    authToken.set("\"$sentryAuthToken\"")

    // The url of your Sentry instance. If you're using SAAS (not self hosting) you do not have to
    // set this. If you are self hosting you can set your URL here
    url = null

    // Disables or enables the handling of Proguard mapping for Sentry.
    // If enabled the plugin will generate a UUID and will take care of
    // uploading the mapping to Sentry. If disabled, all the logic
    // related to proguard mapping will be excluded.
    // Default is enabled.
    includeProguardMapping.set(true)

    // Whether the plugin should attempt to auto-upload the mapping file to Sentry or not.
    // If disabled the plugin will run a dry-run and just generate a UUID.
    // The mapping file has to be uploaded manually via sentry-cli in this case.
    // Default is enabled.
    autoUploadProguardMapping.set(true)

    // Experimental flag to turn on support for GuardSquare's tools integration (Dexguard and External Proguard).
    // If enabled, the plugin will try to consume and upload the mapping file produced by Dexguard and External Proguard.
    // Default is disabled.
    dexguardEnabled.set(false)

    // Disables or enables the automatic configuration of Native Symbols
    // for Sentry. This executes sentry-cli automatically so
    // you don't need to do it manually.
    // Default is disabled.
    uploadNativeSymbols.set(false)

    // Whether the plugin should attempt to auto-upload the native debug symbols to Sentry or not.
    // If disabled the plugin will run a dry-run.
    // Default is enabled.
    autoUploadNativeSymbols.set(true)

    // Does or doesn't include the source code of native code for Sentry.
    // This executes sentry-cli with the --include-sources param. automatically so
    // you don't need to do it manually.
    // Default is disabled.
    includeNativeSources.set(false)

    // Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
    // This enables source context, allowing you to see your source
    // code as part of your stack traces in Sentry.
    includeSourceContext.set(false)

    // Configure additional directories to be included in the source bundle which is used for
    // source context. The directories should be specified relative to the Gradle module/project's
    // root. For example, if you have a custom source set alongside 'main', the parameter would be
    // 'src/custom/java'.
    additionalSourceDirsForSourceContext.set(emptySet())

    // Enable or disable the tracing instrumentation.
    // Does auto instrumentation for specified features through bytecode manipulation.
    // Default is enabled.
    tracingInstrumentation {
        enabled.set(true)

        // Specifies a set of instrumentation features that are eligible for bytecode manipulation.
        // Defaults to all available values of InstrumentationFeature enum class.
        features.set(
            setOf(
                InstrumentationFeature.DATABASE,
                InstrumentationFeature.FILE_IO,
                InstrumentationFeature.OKHTTP,
                InstrumentationFeature.COMPOSE,
            ),
        )

        // Enable or disable logcat instrumentation through bytecode manipulation.
        // Default is enabled.
        logcat {
            enabled.set(true)

            // Specifies a minimum log level for the logcat breadcrumb logging.
            // Defaults to LogcatLevel.WARNING.
            minLevel.set(LogcatLevel.WARNING)
        }

        // The set of glob patterns to exclude from instrumentation. Classes matching any of these
        // patterns in the project's sources and dependencies JARs won't be instrumented by the Sentry
        // Gradle plugin.
        //
        // Don't include the file extension. Filtering is done on compiled classes and
        // the .class suffix isn't included in the pattern matching.
        //
        // Example usage:
        // ```
        // excludes.set(setOf("com/example/donotinstrument/**", "**/*Test"))
        // ```
        //
        // Only supported when using Android Gradle plugin (AGP) version 7.4.0 and above.
        excludes.set(emptySet())
    }

    // Enable auto-installation of Sentry components (sentry-android SDK and okhttp, timber, fragment and compose integrations).
    // Default is enabled.
    // Only available v3.1.0 and above.
    autoInstallation {
        enabled.set(true)

        // Specifies a version of the sentry-android SDK and fragment, timber and okhttp integrations.
        //
        // This is also useful, when you have the sentry-android SDK already included into a transitive dependency/module and want to
        // align integration versions with it (if it's a direct dependency, the version will be inferred).
        //
        // NOTE: if you have a higher version of the sentry-android SDK or integrations on the classpath, this setting will have no effect
        // as Gradle will resolve it to the latest version.
        //
        // Defaults to the latest published Sentry version.
        sentryVersion.set("7.14.0")
    }

    // Disables or enables dependencies metadata reporting for Sentry.
    // If enabled, the plugin will collect external dependencies and
    // upload them to Sentry as part of events. If disabled, all the logic
    // related to the dependencies metadata report will be excluded.
    //
    // Default is enabled.
    //
    includeDependenciesReport.set(true)

    // Whether the plugin should send telemetry data to Sentry.
    // If disabled the plugin won't send telemetry data.
    // This is auto disabled if running against a self hosted instance of Sentry.
    // Default is enabled.
    telemetry.set(true)
}
