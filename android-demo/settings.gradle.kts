pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://jitpack.io")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        google()
        mavenCentral()
    }
}

plugins {
    id("com.github.liushuixiaoxia.cacheext") version "0.0.1" apply true
}

buildCache {

}

include(":app")
include(":nlog")