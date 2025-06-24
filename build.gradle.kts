subprojects {
    group = "com.github.LiushuiXiaoxia"
    version = project.findProperty("$name-version".uppercase())!!

    logger.quiet("setup $project maven $group:$name:$version")
}