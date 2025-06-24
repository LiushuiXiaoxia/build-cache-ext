package com.github.liushuixiaoxia.cacheext

import org.gradle.api.provider.Property
import kotlin.reflect.full.memberProperties

interface Check16KTaskExtension {

    val enable: Property<Boolean>

    val ignoreError: Property<Boolean>
}

fun Check16KTaskExtension.toText(): String {
    val o = this
    val s = Check16KTaskExtension::class.memberProperties
        .associate {
            it.name to (it.get(o) as Property<*>).orNull
        }.toString()
    return "Check16KTaskExtension$s"
}

fun Check16KTaskExtension.checkEnable(): Boolean {
    return enable.getOrElse(true)
}

fun Check16KTaskExtension.isIgnoreError(): Boolean {
    return ignoreError.getOrElse(true)
}

