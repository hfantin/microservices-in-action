package com.github.hfantin.zuulsrv.utils

data class UserContext(
        var correlationId: String = "",
        var authToken: String = "",
        var userId: String = "",
        var organizationId: String = ""
)