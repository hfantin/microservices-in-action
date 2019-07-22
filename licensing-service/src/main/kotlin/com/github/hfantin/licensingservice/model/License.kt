package com.github.hfantin.licensingservice.model

data class License(val id: String,
                   val organizationId: String,
                   val productName: String,
                   val licenseType: String
)