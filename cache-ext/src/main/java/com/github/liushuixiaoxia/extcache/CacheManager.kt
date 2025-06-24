package com.github.liushuixiaoxia.extcache

import org.gradle.api.invocation.Gradle
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object CacheManager {

    lateinit var gradle: Gradle

    lateinit var extBuildCache: ExtBuildCache

    val pool: ExecutorService by lazy {
        Executors.newCachedThreadPool()
    }

    private fun getExtRootDir(): File {
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
        CacheManager.gradle = gradle

    }

    fun setupConfig(config: ExtBuildCache) {
        extBuildCache = config
    }

    fun destroy() {
        pool.shutdown()
    }
}