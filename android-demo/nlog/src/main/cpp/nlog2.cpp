#include <jni.h>
#include <string>
#include "nlog.h"


extern "C" JNIEXPORT void JNICALL
Java_com_example_nlog_NativeLib_nativeTest(JNIEnv *env, jobject thiz) {
    logInfo("nativeTest >>>");
    logInfo("nativeTest <<<");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nlog_NativeLib_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
