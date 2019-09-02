package com.github.hfantin.licensingservice.services

import com.github.hfantin.licensingservice.clients.OrganizationDiscoveryClient
import com.github.hfantin.licensingservice.clients.OrganizationFeingClient
import com.github.hfantin.licensingservice.clients.OrganizationRestTemplateClient
import com.github.hfantin.licensingservice.model.License
import com.github.hfantin.licensingservice.model.Organization
import com.github.hfantin.licensingservice.repository.LicenseRepository
import com.github.hfantin.licensingservice.util.UserContextHolder
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 *
 * In a production system, the Hystrix data that’s most likely to
 * need to be tweaked (timeout parameters, thread pool counts) would be externalized
 * to Spring Cloud Config. This way if you need to change the parameter values,
 * you could change the values and then restart the service instances
 * without having to recompile and redeploy the application.
 *
 */
@Service
// to set configuration for hystrix in class level
//@DefaultProperties(
//        commandProperties = [
//            HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
//        ]
//)
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
                HystrixProperty(name = "coreSize", value = "30"),
                HystrixProperty(name = "maxQueueSize", value = "10")
            ],
            commandProperties = [
                HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
                // example from boot page 140
//                HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10")      // amoount of consecutive calls that must occur within 10 seconde window view before
//                HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75")    // percentage of calls that must fail after requestVolumeThreshold value has been passed before the circuit it triped
//                HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000") // the amount of time that hystrix will sleep once the circuit tripped before allow another call
//                HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000") // controls the size of the window that will be used by hystrix to monitor for problems with a service call
//                HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")             // controls the number of times statistics are collected in the you've defined
            ]
    )
    fun getLicencesByOrganizationId(organizationId: String): MutableList<License> {
        LOG.info("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().correlationId)
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