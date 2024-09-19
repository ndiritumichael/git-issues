plugins {
    alias(libs.plugins.gitissuesmobile.android.library)

    alias(libs.plugins.gitissuesmobile.android.hilt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.devmike.network"
    compileSdk = 34
}

dependencies {

    implementation(libs.apollo.runtime)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)
}
apollo {
    service("service") {
        packageName.set("com.devmike.network")
        introspection {
            endpointUrl.set(
                "https://api.github.com/graphql",
            )
            headers.put(
                "Authorization",
                "Bearer github_pat_11AEHQEHY0PCFsu4h5vEbD_" +
                    "jXD6hrVOV0pVleZoV2TkHlGi0ZU6zFNGn0a" +
                    "QUqo8u7zJCCGJDQWeijeZq1X",
            )
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

/**
* fixes the warning * What went wrong:
* A problem was found with the configuration of task
*':core:network:runKtlintCheckOverMainSourceSet' (type 'KtLintCheckTask').
* - Gradle detected a problem with the following location:
*'C:\Users\User\AndroidStudioProjects\GitIssuesMobile\core\network\build\generated\source\apollo\service'.
* Reason: Task ':core:network:runKtlintCheckOverMainSourceSet' uses this output of task '
*:core:network:generateServiceApolloSources' without declaring an explicit or implicit dependency.
*This can lead to incorrect results being produced, depending on what order the tasks are executed.
*/
tasks.named("runKtlintCheckOverMainSourceSet") {
    dependsOn(tasks.named("generateServiceApolloSources"))
}
