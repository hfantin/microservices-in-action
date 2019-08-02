package com.github.hfantin.licensingservice.services

import com.github.hfantin.licensingservice.clients.OrganizationDiscoveryClient
import com.github.hfantin.licensingservice.clients.OrganizationFeingClient
import com.github.hfantin.licensingservice.clients.OrganizationRestTemplateClient
import com.github.hfantin.licensingservice.model.License
import com.github.hfantin.licensingservice.model.Organization
import com.github.hfantin.licensingservice.repository.LicenseRepository
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

    fun getLicencesByOrganizationId(organizationId: String) = licenseRepository.findByOrganizationId(organizationId)

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