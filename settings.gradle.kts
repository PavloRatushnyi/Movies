pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Movies"
include(":app")
include(":domain")
include(":data")
include(":model")
include(":resource")
include(":shared_composables")
include(":feature:favourite_movies")
include(":feature:popular_movies")
include(":feature:movie_details")
