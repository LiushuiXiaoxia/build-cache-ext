package com.github.liushuixiaoxia.extcache.core

import org.gradle.api.invocation.Gradle
import java.io.File

object CacheConfig {

    lateinit var gradle: Gradle

    fun getExtRootDir(): File {
        return File(gradle.gradleUserHomeDir, "cache-ext").absoluteFile.apply {
            mkdirs()
        }
    }

    fun getCacheDir(): File {
        return File(getExtRootDir(), "cache").absoluteFile.apply {
            mkdirs()
        }
    }

    fun setup(gradle: Gradle) {
        CacheConfig.gradle = gradle
    }
}