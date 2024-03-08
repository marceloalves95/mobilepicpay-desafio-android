pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mobilepicpay-desafio-android"
includeBuild("includedBuilds/jacoco")
include(":app")
include(":libraries:extensions")
include(":libraries:network")
include(":libraries:testing")
