package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


/**
 * Notes from Microservices in Action Book(page 118:
 *
 * When you use the standard Spring RestTemplate class, all service calls’ HTTP status
 * codes will be returned via the ResponseEntity class’s getStatusCode()
 * method. With the Feign Client, any HTTP 4xx – 5xx status codes returned by the service
 * being called will be mapped to a FeignException. The FeignException will
 * contain a JSON body that can be parsed for the specific error message.
 * Feign does provide you the ability to write an error decoder class that will map the
 * error back to a custom Exception class. Writing this decoder is outside the scope
 * of this book, but you can find examples of this in the Feign GitHub repository at
 * (https://github.com/Netflix/feign/wiki/Custom-error-handling).
 *
 */
@Component
class OrganizationRestTemplateClient {

    private val LOG by lazy { LoggerFactory.getLogger(OrganizationRestTemplateClient::class.java) }

    @Autowired
    private lateinit var restTemplate: RestTemplate

//    @Autowired
//    private lateinit var restTemplate: OAuth2RestTemplate


    fun getOrganization(organizationId: String): Organization? {
        LOG.info("getOrganization - id=$organizationId")
        val restExchange = restTemplate.exchange(
//                "http://organizationservice/v1/organizations/{organizationId}",
                "http://zuulservice:5555/api/organization/v1/organizations/{organizationId}",
                HttpMethod.GET, null, Organization::class.java, organizationId)
        LOG.info("getOrganization - restExchange.getBody()=${restExchange.getBody()}")
        return restExchange.body
    }

}