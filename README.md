# build cache ext

---

Gradle plugin for build cache extension.

[![](https://jitpack.io/v/liushuixiaoxia/build-cache-ext.svg)](https://jitpack.io/#liushuixiaoxia/build-cache-ext)

```kotlin
// settings.gradle.kts

pluginManagement {
    repositories {
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    id("com.github.liushuixiaoxia.extcache") version "$version" apply true
}

buildCache {
    remote(com.github.liushuixiaoxia.extcache.ExtBuildCache::class.java) {
        isEnabled = true
        isPush = true
        url = "http://localhost:8080/cache"
    }
}
```