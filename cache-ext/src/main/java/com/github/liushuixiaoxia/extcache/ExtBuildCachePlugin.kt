package com.github.liushuixiaoxia.extcache

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class ExtBuildCachePlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.buildCache {
            it.registerBuildCacheService(ExtBuildCache::class.java, ExtBuildCacheServiceFactory::class.java)
        }

        CacheManager.setup(settings.gradle)
        logQuiet("apply ext build cache plugin success ...")
    }
}