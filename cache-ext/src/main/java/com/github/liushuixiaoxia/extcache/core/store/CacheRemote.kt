package com.github.liushuixiaoxia.extcache.core.store

import com.github.liushuixiaoxia.extcache.CacheManager
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import org.gradle.api.logging.Logging
import java.io.File
import java.util.concurrent.TimeUnit

class CacheRemote(
    private val baseUrl: String,
    private val key: String,
    private val file: File,
    private val fallback404: Boolean,
) : CacheBase<File> {

    private val logger = Logging.getLogger(CacheRemote::class.java)

    companion object {
        private val client: OkHttpClient by lazy {
            val timeout = CacheManager.extBuildCache.timeout
            OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
    }

    private val url: String by lazy {
        "$baseUrl$key"
    }

    override fun exist(): Boolean {
        val ret = kotlin.runCatching {
            val request = okhttp3.Request.Builder()
                .url(url)
                .head()
                .build()
            val response = client.newCall(request).execute()
            response.close()
            response.isSuccessful
        }.onFailure {
            logger.lifecycle("Error checking existence of cache at $url: ${it.message}")
        }

        return if (fallback404) {
            ret.getOrDefault(false)
        } else {
            ret.getOrThrow()
        }
    }

    override fun load(): File? {
        kotlin.runCatching {
            val request = okhttp3.Request.Builder()
                .url(url)
                .get()
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    file.outputStream().buffered().use {
                        response.body?.byteStream()?.copyTo(it)
                    }
                } else {
                    logger.lifecycle("Error loading cache from $url: ${response.message}")
                }
            }
        }.onFailure {
            logger.lifecycle("Error loading cache from $url: ${it.message}")
        }

        if (file.exists()) {
            return file
        }
        return null
    }

    override fun save(s: File) {
        kotlin.runCatching {
            val request = okhttp3.Request.Builder()
                .url(url)
                .put(s.asRequestBody(null))
                .build()
            client.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    logger.lifecycle("Error saving cache to $url: ${it.message}")
                }
            }
        }.onFailure {
            logger.lifecycle("Error saving cache to $url: ${it.message}")
        }
    }
}