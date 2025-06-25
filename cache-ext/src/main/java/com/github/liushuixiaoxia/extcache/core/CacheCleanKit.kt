package com.github.liushuixiaoxia.extcache.core

import com.github.liushuixiaoxia.extcache.logDetail
import com.github.liushuixiaoxia.extcache.logQuiet
import org.gradle.api.logging.Logging
import java.io.File

class CacheCleanKit(
    private val cacheRoot: File,
    private val totalSize: Int,
    private val expiredDay: Int,
) {

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

        val total = cacheRoot.walkBottomUp().filter { it.isFile }.sumOf { it.length() } / 1024 / 1024 / 1024

        if (total > totalSize) {
            logQuiet("local cache size is $total, which exceeds the limit of $totalSize, cleaning up ...")
            cacheRoot.deleteRecursively()
        } else {
            logDetail("local cache size is $total, which is within the limit of $totalSize, no need to clean up.")
        }
    }
}