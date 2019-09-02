package com.github.hfantin.organizationservice.events.source

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface ChangeOrganizationSource {
    @Output("organization-channel")
    fun sendChangeOrganization(): MessageChannel

}