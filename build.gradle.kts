subprojects {
    group = "com.github.liushuixiaoxia"
    version = project.findProperty("$name-version".uppercase())!!

    logger.quiet("setup $project maven $group:$name:$version")
}