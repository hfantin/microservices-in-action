package com.github.hfantin.licensingservice.events.models

import java.io.Serializable

data class OrganizationChangeModel(
        var type: String? = null,
        var action: String? = null,
        var organizationId: String? = null,
        var correlationId: String? = null
): Serializable