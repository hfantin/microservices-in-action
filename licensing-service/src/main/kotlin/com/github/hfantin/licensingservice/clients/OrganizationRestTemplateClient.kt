package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import com.github.hfantin.licensingservice.repository.OrganizationRedisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
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

    private val LOG = LoggerFactory.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var restTemplate: RestTemplate

//    @Autowired
//    private lateinit var restTemplate: OAuth2RestTemplate

    @Autowired
    private lateinit var organizationRedisRepository: OrganizationRedisRepository

    fun checkCacheRedis(organizationId: String) = try {
        LOG.info("get org from redis cache: $organizationId")
        organizationRedisRepository.findOrganization(organizationId)
    } catch (e: Exception) {
        LOG.error("error while trying to get orgs from redis cache: {}", e.message, e)
        null
    }

    fun cacheOrganization(organization: Organization?) = try {
        LOG.info("caching organization in redis cache: $organization")
        organization?.let {
            organizationRedisRepository.saveOrganization(it)
        }
    } catch (e: Exception) {
        LOG.error("error while trying to save organization in redis cache: {}", e.message, e)
    }


    fun getOrganization(organizationId: String): Organization? {
        val organizationFromCache = checkCacheRedis(organizationId)
        LOG.info("getOrganization - id=$organizationId - em cache=$organizationFromCache")
        return organizationFromCache ?: run {
            val restExchange = restTemplate.exchange(
//                "http://organizationservice/v1/organizations/{organizationId}",
                    "http://zuulservice:5555/api/organization/v1/organizations/{organizationId}",
                    HttpMethod.GET, null, Organization::class.java, organizationId)
            LOG.info("getOrganization - restExchange.getBody()=${restExchange.getBody()}")
            val orgFromResource = restExchange.body
            LOG.info("getOrganization - id=$organizationId - buscando no resource=$orgFromResource")
            cacheOrganization(orgFromResource)
            return orgFromResource
        }

    }

}