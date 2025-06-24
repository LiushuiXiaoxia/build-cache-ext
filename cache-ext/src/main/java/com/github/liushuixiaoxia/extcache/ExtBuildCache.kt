package com.github.liushuixiaoxia.extcache

import com.github.liushuixiaoxia.extcache.core.ExtBuildCacheService
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheService
import org.gradle.caching.BuildCacheServiceFactory
import org.gradle.caching.configuration.AbstractBuildCache

abstract class ExtBuildCache : AbstractBuildCache() {

    var url: String? = null

    override fun toString(): String {
        return "ExtBuildCache(url=$url)"
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