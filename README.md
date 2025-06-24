# so-16k-check

---

Android apk so 16k check gradle plugin.

[![](https://jitpack.io/v/liushuixiaoxia/so-16k-check.svg)](https://jitpack.io/#liushuixiaoxia/so-16k-check)

```kotlin
// build.gradle.kts

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// build.gradle.kts
// android application project
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.github.liushuixiaoxia.extcache")
}

dependencies {
    implementation("com.github.liushuixiaoxia:so-16k-check:${version}")
}

check16k {
    enable.set(true) // default is true
    // ignoreError.set(false) // default is true
}
```