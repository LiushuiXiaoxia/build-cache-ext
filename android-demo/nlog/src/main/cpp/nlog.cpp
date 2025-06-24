//
// Created by builder on 2025/3/6.
//

#include "nlog.h"

#include <android/log.h>
#include <jni.h>

#define LOG_TAG "NativeLogger"

void logDebug(const char *msg) {
    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "%s", msg);
}

void logInfo(const char *msg) {
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s", msg);
}

void logError(const char *msg) {
    __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "%s", msg);
}
