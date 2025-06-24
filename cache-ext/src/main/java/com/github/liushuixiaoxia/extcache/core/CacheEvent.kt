package com.github.liushuixiaoxia.extcache.core

import java.io.File
import java.nio.file.Paths

class CacheEvent(
    private val cacheBaseUrl: String,
    private val cacheBaseDir: String,
    private val key: String,
) {

    private val cacheFile: File by lazy {
        if (key.length > 2) {
            Paths.get(cacheBaseDir, key.substring(0, 2), key).toFile()
        } else {
            Paths.get(cacheBaseDir, "--", key).toFile()
        }
    }

    fun save(bytes: ByteArray) {
        cacheFile.parentFile.mkdirs()
        cacheFile.writeBytes(bytes)
    }

    fun exist(): Boolean {
        return cacheFile.exists()
    }

    fun checkValid(): Boolean {
        // todo check gzip
        return cacheFile.exists() && cacheFile.isFile
    }

    fun loadInputStream(): java.io.InputStream? {
        return if (cacheFile.exists()) {
            cacheFile.inputStream().buffered()
        } else {
            null
        }
    }

    fun load(): ByteArray? {
        if (cacheFile.exists()) {
            return cacheFile.readBytes()
        }
        return null
    }
}