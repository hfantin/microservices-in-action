package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
/**
 * this class uses the discovery client to locate te organization service
 */
class OrganizationDiscoveryClient {

    private val LOG by lazy { LoggerFactory.getLogger(OrganizationDiscoveryClient::class.java) }

    @Autowired
    private lateinit var discoveryClient: DiscoveryClient

    fun getOrganization(organizationId: String): Organization? {
        LOG.info("getOrganization $organizationId")
        val restTemplate = RestTemplate()
        val instances = discoveryClient.getInstances("organizationservice")
        LOG.info("getOrganization - instance list $instances")
        if (instances.size == 0) return null
        val serviceUri = "${instances[0].uri.toString()}/v1/organizations/$organizationId"
        LOG.info("getOrganization - service uri $serviceUri")
        val restExchange = restTemplate
                .exchange(serviceUri, HttpMethod.GET, null, Organization::class.java, organizationId)
        return restExchange.getBody()
    }
}