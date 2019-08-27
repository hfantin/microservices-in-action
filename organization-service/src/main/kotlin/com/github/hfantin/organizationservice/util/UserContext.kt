package com.github.hfantin.organizationservice.util

class UserContext(
        var correlationId: String = "",
        var authToken: String = "",
        var userId: String = "",
        var organizationId: String = ""
)

