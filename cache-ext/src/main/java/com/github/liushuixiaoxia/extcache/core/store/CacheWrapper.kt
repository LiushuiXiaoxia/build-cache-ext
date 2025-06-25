package com.github.liushuixiaoxia.extcache.core.store

import com.github.liushuixiaoxia.extcache.CacheManager
import java.io.InputStream

class CacheWrapper(
    private val key: String,
    private val fallback404: Boolean = CacheManager.extBuildCache.fallback404,
    private val cacheBaseDir: String = CacheManager.getCacheDir().absolutePath,
    private val cacheBaseUrl: String? = CacheManager.extBuildCache.cacheUrl,
) : CacheBase<InputStream> {


    private val local: CacheLocal by lazy { CacheLocal(cacheBaseDir, key) }
    private val remote: CacheRemote? by lazy {
        if (cacheBaseUrl.isNullOrBlank()) {
            null
        } else {
            CacheRemote(cacheBaseUrl, key, local.cacheFile, fallback404)
        }
    }

    override fun exist(): Boolean {
        return local.exist() || remote?.exist() == true
    }

    override fun load(): InputStream? {
        if (local.exist()) {
            return local.load()
        }
        if (remote?.exist() == true) {
            return remote?.load()?.inputStream()
        }
        return null
    }

    override fun save(s: InputStream) {
        CacheManager.pool.submit {
            local.save(s)
            remote?.save(local.cacheFile)
        }
    }
}