package com.github.liushuixiaoxia.extcache.core

import com.github.liushuixiaoxia.extcache.CacheManager
import com.github.liushuixiaoxia.extcache.ExtBuildCache
import com.github.liushuixiaoxia.extcache.core.store.CacheWrapper
import com.github.liushuixiaoxia.extcache.logDetail
import com.github.liushuixiaoxia.extcache.logQuiet
import com.github.liushuixiaoxia.extcache.logWarn
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheEntryReader
import org.gradle.caching.BuildCacheEntryWriter
import org.gradle.caching.BuildCacheKey
import org.gradle.caching.BuildCacheService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class ExtBuildCacheService(private val config: ExtBuildCache) : BuildCacheService {

    override fun load(key: BuildCacheKey, reader: BuildCacheEntryReader): Boolean {
        val hash = key.hashCode
        var hilt = false
        val wrapper = CacheWrapper(hash)
        if (wrapper.exist()) {
            val ret = kotlin.runCatching {
                wrapper.load()?.use { input ->
                    reader.readFrom(input)
                }
            }
            if (ret.isSuccess) {
                hilt = true
            } else {
                logWarn("loadCache: key = $hash, error = ${ret.exceptionOrNull()}")
                hilt = false
                if (!config.fallback404) {
                    ret.getOrThrow()
                }
            }
        }
        logDetail("loadCache: key = $hash, hilt = $hilt")
        return hilt
    }

    override fun store(key: BuildCacheKey, writer: BuildCacheEntryWriter) {
        val size = writer.size
        val hash = key.hashCode

        if (size > config.minSize && size < config.maxSize) {
            val data = ByteArrayOutputStream()
            writer.writeTo(data)
            val wrapper = CacheWrapper(hash)
            wrapper.save(ByteArrayInputStream(data.toByteArray()))
            logDetail("storeCache: key = $hash, data = ${data.size()} bytes")
        } else {
            logWarn("storeCache: key = $hash, size = $size bytes, ignore")
        }
    }

    override fun close() {
        logQuiet("Closing ExtBuildCacheService")
        CacheManager.destroy()
    }
}