import com.android.build.gradle.LibraryExtension
import com.configureBaseSetting
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryBaseSettingPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            val extension = extensions.getByType<LibraryExtension>()
            with(extension) {
                defaultConfig.targetSdk = 33
                configureBaseSetting(this)
            }
        }
    }
}