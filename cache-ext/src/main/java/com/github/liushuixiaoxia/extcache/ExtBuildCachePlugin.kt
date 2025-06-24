package com.github.liushuixiaoxia.extcache

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging

class ExtBuildCachePlugin : Plugin<Settings> {

    private val logger = Logging.getLogger(ExtBuildCachePlugin::class.java)

    override fun apply(settings: Settings) {
        settings.buildCache {
            it.registerBuildCacheService(ExtBuildCache::class.java, ExtBuildCacheServiceFactory::class.java)
        }

        logger.quiet("Apply CacheExtPlugin success")
    }
}