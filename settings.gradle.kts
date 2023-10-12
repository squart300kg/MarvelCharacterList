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
include(":data:network")
include(":data:database")
include(":data:repository")
include(":data:work")
include(":domain")
include(":common")
include(":ui")
include(":data:datastore")
