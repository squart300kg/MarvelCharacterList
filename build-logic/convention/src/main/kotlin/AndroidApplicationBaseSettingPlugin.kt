import com.android.build.api.dsl.ApplicationExtension
import com.configureBaseSetting
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationBaseSettingPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            with(extension) {
                defaultConfig.targetSdk = 33
                configureBaseSetting(this)
            }
        }
    }
}