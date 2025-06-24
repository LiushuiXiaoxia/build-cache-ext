package com.github.liushuixiaoxia.cacheext

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheEntryReader
import org.gradle.caching.BuildCacheEntryWriter
import org.gradle.caching.BuildCacheKey
import org.gradle.caching.BuildCacheService
import org.gradle.caching.BuildCacheServiceFactory
import org.gradle.caching.configuration.BuildCache
import java.io.ByteArrayOutputStream

class CacheExtPlugin : Plugin<Settings> {

    private val logger = Logging.getLogger(CacheExtPlugin::class.java)

    override fun apply(settings: Settings) {
        settings.buildCache {
            it.registerBuildCacheService(MyCustomCache::class.java, MyCustomCacheServiceFactory::class.java)
        }

        logger.quiet("Apply CacheExtPlugin success")
    }
}

abstract class MyCustomCache : BuildCache {

    var cacheUrl: String? = null
    override fun toString(): String {
        return "MyCustomCache(cacheUrl=$cacheUrl)"
    }
}

class MyCustomCacheServiceFactory : BuildCacheServiceFactory<MyCustomCache> {

    private val logger = Logging.getLogger(MyCustomCacheServiceFactory::class.java)
    override fun createBuildCacheService(
        config: MyCustomCache,
        describer: BuildCacheServiceFactory.Describer,
    ): BuildCacheService {
        logger.quiet("Creating MyCustomBuildCacheService with config: $config")

        return MyCustomBuildCacheService(config.cacheUrl)
    }
}

class MyCustomBuildCacheService(val url: String?) : BuildCacheService {

    private val logger = Logging.getLogger(MyCustomBuildCacheService::class.java)

    override fun load(key: BuildCacheKey, reader: BuildCacheEntryReader): Boolean {
        val hash = key.hashCode
        logger.quiet("loadCache: key=$hash")
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
