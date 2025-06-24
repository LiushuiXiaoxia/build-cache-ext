plugins {
    `java-library`
    alias(libs.plugins.jetbrains.kotlin.jvm)
    `java-gradle-plugin`
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
}

gradlePlugin {
    plugins {
        create("cacheext") {
            id = "com.github.liushuixiaoxia.extcache"
            implementationClass = "com.github.liushuixiaoxia.extcache.ExtBuildCachePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("build-cache-ext") // 项目名称
                description.set("kotlin/java call process kit")
                url.set("https://github.com/liushuixiaoxia/build-cache-ext")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("liushuixiaoxia")
                        name.set("liushuixiaoxia")
                        url.set("https://github.com/liushuixiaoxia")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/liushuixiaoxia/build-cache-ext.git")
                    developerConnection.set("scm:git:ssh://github.com/liushuixiaoxia/build-cache-ext.git")
                    url.set("https://github.com/liushuixiaoxia/build-cache-ext")
                }
            }
        }
    }
}