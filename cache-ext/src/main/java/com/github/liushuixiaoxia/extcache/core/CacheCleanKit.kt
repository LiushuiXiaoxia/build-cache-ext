package com.github.liushuixiaoxia.extcache.core

import org.gradle.api.logging.Logging
import java.io.File

class CacheCleanKit(
    private val cacheRoot: File,
    private val totalSize: Long,
    private val expiredDay: Int,
) {

    private val logger = Logging.getLogger(CacheCleanKit::class.java)

    fun clean() {
        if (!cacheRoot.exists()) {
            return
        }

        val last = System.currentTimeMillis() - expiredDay * 24 * 60 * 60 * 1000
        cacheRoot.walkBottomUp().filter {
            it.isFile
        }.forEach {
            if (it.lastModified() < last) {
                it.delete()
            }
        }

        val total = cacheRoot.walkBottomUp().filter { it.isFile }.sumOf { it.length() }

        if (total > totalSize) {
            logger.quiet("local cache size is $total, which exceeds the limit of $totalSize, cleaning up ...")
            cacheRoot.deleteRecursively()
        } else {
            logger.lifecycle("local cache size is $total, which is within the limit of $totalSize, no need to clean up.")
        }
    }
}