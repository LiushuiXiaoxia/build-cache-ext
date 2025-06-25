# build cache ext

---

Gradle plugin for build cache extension.

[![](https://jitpack.io/v/liushuixiaoxia/build-cache-ext.svg)](https://jitpack.io/#liushuixiaoxia/build-cache-ext)

Gradle 插件，用于扩展 Gradle 构建缓存功能。经常会用到 gradle 缓存，官方提供 local 和 remote 两种， 但是有时候会有一些问题，需要更灵活的配置等。

* 本地缓存清理麻烦，需要手动清理，或者需要配置 cron 定时任务。
* 远程无法控制更细的颗粒度，如超时等。
* 当缓存访问失败(比如cpu，网络，内存等问题)，会有重试，如果多次失败，则后续直接不使用, 会导致后续无法使用缓存。

所以此插件主要解决以上问题，可以有更细的颗粒度控制缓存，如超时、重试次数、缓存大小、缓存过期时间、缓存清理策略等。

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
    // 本地缓存建议禁用，因为 ExtBuildCache 也有本地缓存功能
    local {
        isEnabled = false
        isPush = false
    }

    remote(com.github.liushuixiaoxia.extcache.ExtBuildCache::class.java) {
        // 通用配置
        isEnabled = true // 开启缓存
        isPush = true // 推送缓存

        // 本地缓存配置 
        // cacheDir = "" // 缓存路径, 默认为 ~/.gradle/cache-ext/cache
        // minSize = 0 // 缓存最小大小，默认为0
        // maxSize = 0 // 缓存最大大小，默认为1G
        // expiredDay = 7 //  本地缓存过期时间，单位为天，默认为7天
        // maxTotalSizeG = 100 // 缓存最大总大小，单位为G，默认为100G，按天清理，超过100G的缓存会清理全部

        // 远程缓存配置
        cacheUrl = "http://localhost:22333/gradle-cache/" // 设置缓存地址, 请替换为实际地址，不设置则不使用远程缓存
        // timeout = 10 // 设置缓存请求超时时间，单位为秒，默认为10秒 
        fallback404 = true // 设置当远程服务请求失败，降级为404，防止异常导致后续无法使用缓存
        retryCount = 2 // 设置请求失败重试次数，默认为2次

        // 其他配置
        // logDetail = true // 显示详细日志信息，默认为true
    }
}
```

```groovy
// settings.gradle
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.liushuixiaoxia:build-cache-ext:$version'
    }
}

apply plugin: "com.github.liushuixiaoxia.extcache"

buildCache {
    local {
        enabled = false
    }
    remote(com.github.liushuixiaoxia.extcache.ExtBuildCache) {
        enabled = true
        push = true
        
        // 相关配置参照 gradle kts 用法
        timeout = 10
        fallback404 = true
        cacheUrl = "http://localhost:22333/gradle-cache/"
    }
}

```