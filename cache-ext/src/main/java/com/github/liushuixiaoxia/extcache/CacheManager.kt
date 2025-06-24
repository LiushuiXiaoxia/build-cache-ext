package com.github.liushuixiaoxia.extcache

import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.Logging
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object CacheManager {

    val logger = Logging.getLogger(CacheManager::class.java)

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

fun logDetail(message: String) {
    if (CacheManager.extBuildCache.logDetail) {
        CacheManager.logger.lifecycle("[BuildCacheExt] $message")
    }
}

fun logQuiet(message: String) {
    CacheManager.logger.quiet("[BuildCacheExt] $message")
}

fun logWarn(message: String) {
    CacheManager.logger.warn("[BuildCacheExt] $message")
}

fun logError(message: String) {
    CacheManager.logger.error("[BuildCacheExt] $message")
}