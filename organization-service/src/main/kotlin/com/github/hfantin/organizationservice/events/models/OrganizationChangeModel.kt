package com.github.hfantin.organizationservice.events.models

class OrganizationChangeModel(
        var type: String? = null,
        var action: String? = null,
        var organizationId: String? = null,
        var correlationId: String? = null
)