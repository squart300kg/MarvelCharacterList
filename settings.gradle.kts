pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs")
    }
}

rootProject.name = "KoreanInvestment"
include(":app")
include(":ui:home")
include(":ui:bookmark")
include(":ui:base")
include(":data:network")
include(":data:database")
include(":data:repository")
include(":data:work")
include(":domain")
include(":common")
