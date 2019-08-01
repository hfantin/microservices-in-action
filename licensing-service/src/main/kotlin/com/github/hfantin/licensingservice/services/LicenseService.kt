package com.github.hfantin.licensingservice.services

import com.github.hfantin.licensingservice.repository.LicenseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
class LicenseService {

    @Autowired
    private lateinit var licenceRepository: LicenseRepository

    //    @RequestMapping("/{licenseId}", method = [RequestMethod.GET])
    @GetMapping("/{licenseId}")
    fun getLicence(@PathVariable("organizationId") organizationId: String,
                   @PathVariable("licenseId") licenseId: String) = licenceRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

    /**
     * this method is only to help testing the ribbon client type
     *
     * @param clientType - Discovery - Uses Spring RestTemplate to invoke organization service
     *                     Rest      - Uses an enhanced Spring RestTemplate to invoke the Ribbon-based service
     *                     Feing     - Uses Netflix’s Feign client library to invoke a service via Ribbon
     */
    @GetMapping("/{licenseId}/{clientType}")
    fun getLicenseWithClient(
            @PathVariable("organizationId") organizationId: String,
            @PathVariable("licenseId") licenseId: String,
            @PathVariable("clientType") clientType: String
    ) = "TODO"
    //licenseService.getLicense(organizationId, licenseId, clientType);

    //    @RequestMapping(method = [RequestMethod.GET])
    @GetMapping
    fun getLicencesByOrganizationId(@PathVariable("organizationId") organizationId: String) = licenceRepository.findByOrganizationId(organizationId)


    @RequestMapping("{licenseId}", method = [RequestMethod.PUT])
    fun updateLicenses(@PathVariable("licenseId") licenseId: String) = "This is the put"


    @RequestMapping("{licenseId}", method = [RequestMethod.POST])
    fun saveLicenses(@PathVariable("licenseId") licenseId: String) = "This is the post"

    @RequestMapping("{licenseId}", method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLicenses(@PathVariable("licenseId") licenseId: String) = "This is the Delete"

}