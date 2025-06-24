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
    compileOnly(libs.agpDev)
    implementation(libs.processKit)
}

gradlePlugin {
    plugins {
        create("cacheext") {
            id = "com.github.liushuixiaoxia.cacheext"
            implementationClass = "com.github.liushuixiaoxia.cacheext.CacheExtPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("so-16k-check") // 项目名称
                description.set("kotlin/java call process kit")
                url.set("https://github.com/LiushuiXiaoxia/so-16k-check")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("LiushuiXiaoxia")
                        name.set("LiushuiXiaoxia")
                        url.set("https://github.com/LiushuiXiaoxia")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/LiushuiXiaoxia/so-16k-check.git")
                    developerConnection.set("scm:git:ssh://github.com/LiushuiXiaoxia/so-16k-check.git")
                    url.set("https://github.com/LiushuiXiaoxia/so-16k-check")
                }
            }
        }
    }
}