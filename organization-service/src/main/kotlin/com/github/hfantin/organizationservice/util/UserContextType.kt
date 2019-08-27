package com.github.hfantin.organizationservice.util

enum class UserContextType(val key: String) {
    CORRELATION_ID("tmx-correlation-id"),
    AUTH_TOKEN("tmx-auth-token"),
    USER_ID("tmx-user-id"),
    ORG_ID("tmx-org-id")
}