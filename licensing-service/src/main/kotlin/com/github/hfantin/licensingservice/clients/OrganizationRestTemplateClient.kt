package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OrganizationRestTemplateClient {

    private val LOG by lazy { LoggerFactory.getLogger(OrganizationRestTemplateClient::class.java) }

    @Autowired
    private lateinit var restTemplate: RestTemplate


    fun getOrganization(organizationId: String): Organization? {
        val restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET, null, Organization::class.java, organizationId)
        return restExchange.getBody()
    }

}