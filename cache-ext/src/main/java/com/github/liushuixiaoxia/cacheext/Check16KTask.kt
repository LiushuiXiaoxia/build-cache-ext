package com.github.liushuixiaoxia.cacheext

import com.github.liushuixiaoxia.processkit.ProcessKit
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File


@DisableCachingByDefault(because = "This task is not cacheable because it depends on the APK file which may change frequently.")
abstract class Check16KTask : DefaultTask() {

    private val logger = Logging.getLogger(Check16KTask::class.java)

    @Internal
    abstract fun getBuildDir(): DirectoryProperty

    @Internal
    abstract fun getApkDir(): DirectoryProperty

    @Internal
    var ignoreError: Boolean = true

    @TaskAction
    fun action() {
        val f = getApkDir().get().asFile
        logger.lifecycle("apkDir = $f")
        val apk = f.walkTopDown().firstOrNull { it.name.endsWith(".apk") }
        if (apk == null || !apk.exists()) {
            throw RuntimeException("apk is not found, apk = $apk")
        }
        logger.lifecycle("apk = $apk")

        val shell = File(getBuildDir().get().asFile, "intermediates/check-so/check-so.sh").apply {
            parentFile.mkdirs()
        }
        val s = this.javaClass.classLoader.getResourceAsStream("check-so.sh")?.bufferedReader()?.readText()
            ?: throw RuntimeException("check-so.sh is not found")
        shell.writeText(s)

        val ret = ProcessKit.run("bash ${shell.absolutePath} ${apk.absolutePath}")
        if (ret == 0) {
            logger.quiet("$name success")
        } else {
            if (ignoreError) {
                logger.quiet("$name fail, but ignore")
            } else {
                logger.quiet("$name fail")
                throw RuntimeException("$name fail")
            }
        }
    }
}