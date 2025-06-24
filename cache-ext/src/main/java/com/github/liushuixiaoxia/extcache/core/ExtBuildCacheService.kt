package com.github.liushuixiaoxia.extcache.core

import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheEntryReader
import org.gradle.caching.BuildCacheEntryWriter
import org.gradle.caching.BuildCacheKey
import org.gradle.caching.BuildCacheService
import java.io.ByteArrayOutputStream

class ExtBuildCacheService(val url: String?) : BuildCacheService {

    private val logger = Logging.getLogger(ExtBuildCacheService::class.java)

    override fun load(key: BuildCacheKey, reader: BuildCacheEntryReader): Boolean {
        val hash = key.hashCode
        logger.quiet("loadCache: key=$hash, displayName = ${key.displayName}")
        return false
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