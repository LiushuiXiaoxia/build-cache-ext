//
// Created by builder on 2025/3/6.
//

#ifndef CPP_DEMO_NATIVE_LOGGER_H
#define CPP_DEMO_NATIVE_LOGGER_H

#ifdef __cplusplus
extern "C" {
#endif

void logDebug(const char *msg);
void logInfo(const char *msg);
void logError(const char *msg);

#ifdef __cplusplus
}
#endif

#endif //CPP_DEMO_NATIVE_LOGGER_H
