package com.github.liushuixiaoxia.extcache

import com.github.liushuixiaoxia.extcache.core.ExtBuildCacheService
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheService
import org.gradle.caching.BuildCacheServiceFactory
import org.gradle.caching.configuration.AbstractBuildCache
import kotlin.reflect.full.memberProperties

abstract class ExtBuildCache : AbstractBuildCache() {

    var url: String? = null

    var timeout: Long = 10_000

    var retryCount: Int = 2

    var fallback404: Boolean = true

    override fun toString(): String {
        val map = mutableMapOf<String, Any?>()
        ExtBuildCache::class.memberProperties.forEach {
            map[it.name] = it.get(this)
        }
        return "ExtBuildCache($map)"
    }
}

class ExtBuildCacheServiceFactory : BuildCacheServiceFactory<ExtBuildCache> {

    private val logger = Logging.getLogger(ExtBuildCacheServiceFactory::class.java)
    override fun createBuildCacheService(
        config: ExtBuildCache,
        describer: BuildCacheServiceFactory.Describer,
    ): BuildCacheService {
        logger.quiet("Creating ExtBuildCacheService with config: $config")

        return ExtBuildCacheService(config.url)
    }
}