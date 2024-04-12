import com.android.build.api.dsl.ApplicationExtension
import kr.co.architecture.build.logic.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            extension.apply {
                buildFeatures { compose = true }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.11"
                }

                dependencies {
                    val bom = libs.findLibrary("androidx-compose-bom").get()
                    add("implementation", platform(bom))
//                    add("implementation", libs.findLibrary("androidx-activity-compose").get())
//                    add("implementation", libs.findLibrary("androidx-compose-ui").get())
//                    add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
//                    add("implementation", libs.findLibrary("androidx-compose-ui-preview").get())
//                    add("implementation", libs.findLibrary("androidx-navigation-compose").get())
                    add("implementation", libs.findLibrary("androidx-compose-material").get())
                    add("implementation", libs.findLibrary("androidx-compose-material3").get())
                    add("implementation", libs.findLibrary("androidx-compose-material3-adaptive").get())
                    add("implementation", libs.findLibrary("androidx-compose-material3-windowSizeClass").get())
                    add("implementation", libs.findLibrary("androidx-compose-material3-adaptive-layout").get())
                    add("implementation", libs.findLibrary("androidx-compose-material3-adaptive-navigation").get())
//                    add("implementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                    add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                    add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
//                    add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
//                    add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                    add("implementation", libs.findLibrary("androidx-paging-compose").get())

                    add("androidTestImplementation", platform(bom))
                    add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())

                    add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
                    add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                    add("debugImplementation", libs.findLibrary("androidx-compose-ui-testManifest").get())
                }
            }
        }
    }
}