package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


/**
 * this class uses the discovery client to locate te organization service
 *
 * Notes from Microservices in Action Book(page 114):
 *
 * I’m walking through the DiscoveryClient to be completed in our discussion of building
 * service consumers with Ribbon. The reality is that you should only use the Discovery-
 * Client directly when your service needs to query Ribbon to understand what services
 * and service instances are registered with it. There are several problems with this
 * code including the following:
 * You aren’t taking advantage of Ribbon’s client side load-balancing—By calling the DiscoveryClient
 * directly, you get back a list of services, but it becomes your responsibility
 * to choose which service instances returned you’re going to invoke.
 * You’re doing too much work—Right now, you have to build the URL that’s going to be
 * used to call your service. It’s a small thing, but every piece of code that you can avoid
 * writing is one less piece of code that you have to debug.
 * Observant Spring developers might have noticed that you’re directly instantiating the
 * RestTemplate class in the code. This is antithetical to normal Spring REST invocations,
 * as normally you’d have the Spring Framework inject the RestTemplate the class
 * using it via the @Autowired annotation.
 * You instantiated the RestTemplate class in listing 4.8 because once you’ve enabled
 * the Spring DiscoveryClient in the application class via the @EnableDiscoveryClient
 * annotation, all RestTemplates managed by the Spring framework will have
 * a Ribbon-enabled interceptor injected into them that will change how URLs are created with
 * the RestTemplate class. Directly instantiating the RestTemplate class allows you to avoid this behavior.
 * In summary, there are better mechanisms for calling a Ribbon-backed service
 */
@Component
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