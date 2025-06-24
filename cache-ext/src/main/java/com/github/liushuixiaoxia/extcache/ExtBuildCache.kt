package com.github.liushuixiaoxia.extcache

import com.github.liushuixiaoxia.extcache.core.ExtBuildCacheService
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheService
import org.gradle.caching.BuildCacheServiceFactory
import org.gradle.caching.configuration.AbstractBuildCache
import kotlin.reflect.full.memberProperties

abstract class ExtBuildCache : AbstractBuildCache() {

    var url: String? = null

    var timeout: Long = 10

    var retryCount: Int = 2

    var fallback404: Boolean = true

    fun checkValid(): Boolean {
        if (url.isNullOrBlank()) {
            throw IllegalArgumentException("Url must not be null or blank")
        }

        if (timeout > 3600 || timeout < 0) {
            throw IllegalArgumentException("Timeout must be between 0 and 3600 seconds")
        }

        if (retryCount < 0 || retryCount > 10) {
            throw IllegalArgumentException("Retry count must be between 0 and 10")
        }
        return true
    }

    override fun toString(): String {
        val map = linkedMapOf<String, Any?>()
        ExtBuildCache::class.memberProperties.sortedBy { it.name }.forEach {
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
        logger.quiet("Creating ext build cache with config: $config")
        config.checkValid()

        return ExtBuildCacheService(config)
    }
}