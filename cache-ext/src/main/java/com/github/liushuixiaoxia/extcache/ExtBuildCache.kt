package com.github.liushuixiaoxia.extcache

import com.github.liushuixiaoxia.extcache.core.ExtBuildCacheService
import org.gradle.api.logging.Logging
import org.gradle.caching.BuildCacheService
import org.gradle.caching.BuildCacheServiceFactory
import org.gradle.caching.configuration.AbstractBuildCache
import kotlin.reflect.full.memberProperties

abstract class ExtBuildCache : AbstractBuildCache() {

    var minSize: Int = 0

    var maxSize: Int = 1024 * 1024 * 1024 // 1G

    var expiredDay: Long = 7 // days

    var maxTotalSizeG = 20 // 10G

    var url: String? = null

    var timeout: Long = 10

    var retryCount: Int = 2

    var fallback404: Boolean = true

    var logDetail: Boolean = false

    fun checkValid(): Boolean {
        // common config
        if (minSize < 0) {
            throw IllegalArgumentException("Min size must be greater than or equal to 0")
        }
        if (maxSize < 0) {
            throw IllegalArgumentException("Max size must be greater than or equal to 0")
        }
        if (minSize > maxSize) {
            throw IllegalArgumentException("Min size must be less than or equal to max size")
        }

        if (expiredDay <= 0) {
            throw IllegalArgumentException("Expired day must be greater than 0")
        }

        if (maxTotalSizeG <= 0) {
            throw IllegalArgumentException("Max total size must be greater than 0")
        }

        // remote cache config
        if (!url.isNullOrBlank()) {
            if (!url!!.startsWith("http://") && !url!!.startsWith("https://")) {
                throw IllegalArgumentException("Url must start with http:// or https://")
            }
            if (!url!!.endsWith("/")) {
                throw IllegalArgumentException("Url must end with /")
            }

            if (timeout > 3600 || timeout < 0) {
                throw IllegalArgumentException("Timeout must be between 0 and 3600 seconds")
            }

            if (retryCount < 0 || retryCount > 10) {
                throw IllegalArgumentException("Retry count must be between 0 and 10")
            }
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