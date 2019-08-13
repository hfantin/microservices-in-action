package com.github.hfantin.zuulsrv.model

data class AbTestingRoute(
        var serviceName: String = "",
        var active: String = "",
        var endpoint: String = "",
        var weight: Int? = null
)