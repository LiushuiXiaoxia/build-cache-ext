package com.github.liushuixiaoxia.extcache.core

import com.github.liushuixiaoxia.extcache.ExtBuildCache
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheEntryReader
import org.gradle.caching.BuildCacheEntryWriter
import org.gradle.caching.BuildCacheKey
import org.gradle.caching.BuildCacheService
import java.io.ByteArrayOutputStream

class ExtBuildCacheService(val config: ExtBuildCache) : BuildCacheService {

    private val logger = Logging.getLogger(ExtBuildCacheService::class.java)

    override fun load(key: BuildCacheKey, reader: BuildCacheEntryReader): Boolean {
        val hash = key.hashCode
        var hilt = false
        val cacheEvent = CacheEvent(config.url!!, CacheConfig.getCacheDir().absolutePath, hash)
        if (cacheEvent.exist() && cacheEvent.checkValid()) {
            val ret = kotlin.runCatching {
                cacheEvent.loadInputStream()?.use { input ->
                    reader.readFrom(input)
                }
            }
            if (ret.isSuccess) {
                hilt = true
            } else {
                logger.quiet("loadCache: key=$hash, error = ${ret.exceptionOrNull()}")
                hilt = false
                if (!config.fallback404) {
                    ret.getOrThrow()
                }
            }
        }
        logger.lifecycle("loadCache: key=$hash, hilt = $hilt")
        return hilt
    }

    override fun store(key: BuildCacheKey, writer: BuildCacheEntryWriter) {
        val size = writer.size
        val hash = key.hashCode

        if (size > config.minSize && size < config.maxSize) {
            val data = ByteArrayOutputStream()
            writer.writeTo(data)
            val cacheEvent = CacheEvent(config.url!!, CacheConfig.getCacheDir().absolutePath, hash)
            cacheEvent.save(data.toByteArray())
            logger.lifecycle("storeCache: key=$hash, data = ${data.size()} bytes")
        } else {
            logger.quiet("storeCache: key=$hash, size = $size bytes, ignore")
        }
    }

    override fun close() {
        logger.quiet("Closing MyCustomBuildCacheService")
    }
}