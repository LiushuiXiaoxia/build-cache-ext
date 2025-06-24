package com.github.liushuixiaoxia.extcache.core.store

import java.io.File
import java.io.InputStream
import java.nio.file.Paths

class CacheLocal(
    private val cacheBaseDir: String,
    private val key: String,
) : CacheBase<InputStream> {

    val cacheFile: File by lazy {
        if (key.length > 2) {
            Paths.get(cacheBaseDir, key.substring(0, 2), key).toFile()
        } else {
            Paths.get(cacheBaseDir, "--", key).toFile()
        }
    }

    override fun save(s: InputStream) {
        cacheFile.parentFile.mkdirs()
        cacheFile.outputStream().buffered().use {
            s.buffered().copyTo(it)
        }
    }

    override fun exist(): Boolean {
        return cacheFile.exists() && cacheFile.isFile
    }

    override fun load(): InputStream? {
        return if (cacheFile.exists()) {
            cacheFile.inputStream().buffered()
        } else {
            null
        }
    }
}