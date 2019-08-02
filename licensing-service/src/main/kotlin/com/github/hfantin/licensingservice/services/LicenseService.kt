package com.github.hfantin.licensingservice.services

import com.github.hfantin.licensingservice.clients.OrganizationDiscoveryClient
import com.github.hfantin.licensingservice.clients.OrganizationFeingClient
import com.github.hfantin.licensingservice.clients.OrganizationRestTemplateClient
import com.github.hfantin.licensingservice.model.License
import com.github.hfantin.licensingservice.model.Organization
import com.github.hfantin.licensingservice.repository.LicenseRepository
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class LicenseService {

    private val LOG by lazy { LoggerFactory.getLogger(LicenseService::class.java) }

    @Autowired
    private lateinit var licenseRepository: LicenseRepository

    @Autowired
    private lateinit var organizationFeignClient: OrganizationFeingClient

    @Autowired
    private lateinit var organizationRestClient: OrganizationRestTemplateClient

    @Autowired
    private lateinit var organizationDiscoveryClient: OrganizationDiscoveryClient

    /**
     * Any time this method is called, the call will be wrapped with a Hystrix circuit breaker.
     * The call will be interrupted if it takes more them 1000 miliseconds
     */
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licencesByOrganizationIdThreadPool",
            threadPoolProperties = [
                HystrixProperty(name="coreSize", value="30"),
                HystrixProperty(name="maxQueueSize", value="10")
            ],
            commandProperties = [
                HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
            ]
    )
    fun getLicencesByOrganizationId(organizationId: String): MutableList<License> {
        randomlyRunLong()
        return licenseRepository.findByOrganizationId(organizationId)
    }

    private fun randomlyRunLong() {
        val random = Random().nextInt(3) + 1
        val ms = 11_000L
        LOG.info("Call number $random")
        if (random == 3) {
            LOG.info("I am going to sleep for $ms miliseconds....")
            try {
                Thread.sleep(ms)
            } catch (e: InterruptedException) {
                LOG.error("thread interrupted: ", e.message)
            }

        }
    }

    /**
     * The fallback method must have the same signature of the original method and resides in the same class
     */
    private fun buildFallbackLicenseList(organizationId: String) = listOf(License("0", organizationId, "Sorry, no licensing information currently available"))

    fun findByOrganizationIdAndLicenseId(organizationId: String, licenseId: String) = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

    fun getLicenseWithClient(organizationId: String, licenseId: String, clientType: String): License? {
        LOG.info("getLicenseWithClient $organizationId $licenseId $clientType")
        val license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
        LOG.info("license $license")
        return license?.apply {
            val org = retrieveOrgInfo(organizationId, clientType)
            LOG.info("retrieved organization: $org")
            org?.let {
                organizationName = it.name
                contactName = it.contactName
                contactEmail = it.contactEmail
                contactPhone = it.contactPhone
            }

        }
    }

    fun save(license: License) {
        license.licenseId = UUID.randomUUID().toString()
        licenseRepository.save(license)
    }

    fun update(license: License) = licenseRepository.save(license)

    fun delete(licenseId: String) = licenseRepository.deleteById(licenseId)


    private fun retrieveOrgInfo(organizationId: String, clientType: String): Organization? =
            when (clientType) {
                "feign" -> {
                    LOG.info("I am using the feign client")
                    organizationFeignClient.getOrganization(organizationId)
                }
                "rest" -> {
                    LOG.info("I am using the rest client")
                    organizationRestClient.getOrganization(organizationId)
                }
                "discovery" -> {
                    LOG.info("I am using the discovery client")
                    organizationDiscoveryClient.getOrganization(organizationId)
                }
                else -> organizationRestClient.getOrganization(organizationId)
            }

}