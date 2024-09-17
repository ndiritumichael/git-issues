
import com.android.build.api.dsl.ApplicationExtension
import com.devmike.gitissuesmobile.configureAndroidCompose
import com.devmike.gitissuesmobile.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin = "com.android.application")
                apply(plugin = "org.jetbrains.kotlin.android")
                apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<ApplicationExtension>()

            configureAndroidCompose(extension)
            configureKotlinAndroid(extension)
        }
    }
}
