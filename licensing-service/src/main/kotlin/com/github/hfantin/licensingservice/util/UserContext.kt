package com.github.hfantin.licensingservice.util

data class UserContext(
        var correlationId: String = "",
        var authToken: String = "",
        var userId: String = "",
        var organizationId: String = ""
)