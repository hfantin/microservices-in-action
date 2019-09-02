package com.github.hfantin.organizationservice.events.source

import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(ChangeOrganizationSource::class)
class ChangeOrganizationImpl