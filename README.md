# so-16k-check

---

Android apk so 16k check gradle plugin.

[![](https://jitpack.io/v/LiushuiXiaoxia/so-16k-check.svg)](https://jitpack.io/#LiushuiXiaoxia/so-16k-check)

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

    id("com.github.liushuixiaoxia.cacheext")
}

dependencies {
    implementation("com.github.LiushuiXiaoxia:so-16k-check:${version}")
}

check16k {
    enable.set(true) // default is true
    // ignoreError.set(false) // default is true
}
```


```text
# Execution success.
extension = Check16KTaskExtension{enable=true, ignoreError=true}
 checkSo16k: 
 checkSo16k: Recursively analyzing /Volumes/d/ws2/github/so-16k-check/android-demo/app/build/outputs/apk/debug/app-debug.apk
 checkSo16k: 
 checkSo16k: === APK zip-alignment ===
 checkSo16k:  4238112 lib/arm64-v8a/libc++_shared.so (OK - compressed)
 checkSo16k:  4689096 lib/arm64-v8a/libnlog.so (OK - compressed)
 checkSo16k:  4705637 lib/x86_64/libc++_shared.so (OK - compressed)
 checkSo16k:  5165382 lib/x86_64/libnlog.so (OK - compressed)
 checkSo16k: Verification successful
 checkSo16k: =========================
 checkSo16k: 
 checkSo16k: === ELF alignment ===
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.gHf63goknv/lib/arm64-v8a/libc++_shared.so: ALIGNED (2**14)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.gHf63goknv/lib/arm64-v8a/libnlog.so: ALIGNED (2**14)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.gHf63goknv/lib/x86_64/libc++_shared.so: ALIGNED (2**14)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.gHf63goknv/lib/x86_64/libnlog.so: ALIGNED (2**14)
 checkSo16k: ELF Verification Successful
 checkSo16k: =====================
 checkSo16k: Execution succeeded.
```

```
# Execution failed.
extension = Check16KTaskExtension{enable=true, ignoreError=false}
 checkSo16k: 
 checkSo16k: Recursively analyzing /Volumes/d/ws2/github/so-16k-check/android-demo/app/build/outputs/apk/debug/app-debug.apk
 checkSo16k: 
 checkSo16k: === APK zip-alignment ===
 checkSo16k:  4243456 lib/arm64-v8a/libc++_shared.so (OK)
 checkSo16k:  5537792 lib/arm64-v8a/libnlog.so (OK)
 checkSo16k:  7798784 lib/x86_64/libc++_shared.so (OK)
 checkSo16k:  9060352 lib/x86_64/libnlog.so (OK)
 checkSo16k: Verification successful
 checkSo16k: =========================
 checkSo16k: 
 checkSo16k: === ELF alignment ===
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/armeabi-v7a/libc++_shared.so: UNALIGNED (2**12)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/armeabi-v7a/libnlog.so: UNALIGNED (2**12)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/x86/libc++_shared.so: UNALIGNED (2**12)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/x86/libnlog.so: UNALIGNED (2**12)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/arm64-v8a/libc++_shared.so: ALIGNED (2**14)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/arm64-v8a/libnlog.so: UNALIGNED (2**12)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/x86_64/libc++_shared.so: ALIGNED (2**14)
 checkSo16k: /var/folders/r3/1d5nykbj0_957hqhv1cv1qym0000gn/T/app-debug_out_XXXXX.yIFnlCiVlu/lib/x86_64/libnlog.so: UNALIGNED (2**12)
 checkSo16k: Found 6 unaligned libs (only arm64-v8a/x86_64 libs need to be aligned).
 checkSo16k: =====================
 checkSo16k: Execution failed.

> Task :app:checkDebugSo16k FAILED
apkDir = /Volumes/d/ws2/github/so-16k-check/android-demo/app/build/outputs/apk/debug
apk = /Volumes/d/ws2/github/so-16k-check/android-demo/app/build/outputs/apk/debug/app-debug.apk
checkDebugSo16k fail
```