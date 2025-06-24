package com.github.liushuixiaoxia.extcache.core.store

interface CacheBase<S> {

    fun exist(): Boolean

    fun save(s: S)

    fun load(): S?
}