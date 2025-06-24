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
        val ret = false
        logger.quiet("loadCache: key=$hash, ret = $ret")
        return ret
    }

    override fun store(key: BuildCacheKey, writer: BuildCacheEntryWriter) {
        val data = ByteArrayOutputStream()
        writer.writeTo(data)
        val hash = key.hashCode
        logger.quiet("storeCache: key=$hash, data = ${data.size()} bytes")
    }

    override fun close() {
        logger.quiet("Closing MyCustomBuildCacheService")
    }
}