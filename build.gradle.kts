plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

subprojects {
    group = "com.github.LiushuiXiaoxia"
    version = project.findProperty("$name-version".uppercase())!!

    logger.quiet("setup $project maven $group:$name:$version")
}