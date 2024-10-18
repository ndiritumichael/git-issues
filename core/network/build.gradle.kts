import java.util.Properties

plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.apollo)
    alias(libs.plugins.gitissuesmobile.android.library.jacoco)
}

android {
    namespace = "com.devmike.network"
    compileSdk = 34
}

dependencies {

    implementation(libs.apollo.runtime)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)

    testImplementation(libs.apollo.mockserver)
    testImplementation(libs.truth)

    testImplementation(libs.apollo.testing.support)
}

val keystoreFile = project.rootProject.file("local.properties")
val properties = Properties()
properties.load(keystoreFile.inputStream())

val gitdevtokenn = properties.getProperty("GITDEVTOKEN") ?: ""

/**
 * Apollo service

 */

apollo {
    service("service") {
        packageName.set("com.devmike.network")
        introspection {
            endpointUrl.set(
                "https://api.github.com/graphql",
            )
            headers.put(
                "Authorization",
                "Bearer $gitdevtokenn",
            )
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

jacoco {
    toolVersion = "0.8.4"
}

tasks.withType<Test> {
    //   jacoco. = true
}

tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*") // + exclusions
    }
}
