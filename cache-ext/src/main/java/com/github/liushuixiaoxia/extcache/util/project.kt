package com.github.liushuixiaoxia.extcache.util

import org.gradle.api.Project

fun Project.isAndroidApp(): Boolean {
    return this.plugins.hasPlugin("com.android.application")
}