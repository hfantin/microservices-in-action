package com.github.hfantin.licensingservice.controller

import com.github.hfantin.licensingservice.model.License
import com.github.hfantin.licensingservice.services.LicenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
class LicenseServiceController {


    @Autowired
    private lateinit var licenseService: LicenseService

    @GetMapping
    fun getLicencesByOrganizationId(@PathVariable("organizationId") organizationId: String) = licenseService.getLicencesByOrganizationId(organizationId)

    @GetMapping("/{licenseId}")
    fun findByOrganizationIdAndLicenseId(@PathVariable organizationId: String,
                                         @PathVariable licenseId: String) = licenseService.findByOrganizationIdAndLicenseId(organizationId, licenseId)

    /*
     * this method is only to help testing the ribbon client type
     *
     * @param clientType - Discovery - Uses Spring RestTemplate to invoke organization service
     *                     Rest      - Uses an enhanced Spring RestTemplate to invoke the Ribbon-based service
     *                     Feing     - Uses Netflix’s Feign client library to invoke a service via Ribbon
     */
    @GetMapping("/{licenseId}/{clientType}")
    fun getLicenseWithClient(@PathVariable organizationId: String, @PathVariable licenseId: String, @PathVariable clientType: String) = licenseService.getLicenseWithClient(organizationId, licenseId, clientType);


    @PostMapping("{licenseId}")
    fun saveLicenses(@PathVariable licenseId: String, @RequestBody license: License) = licenseService.save(license)


    @PutMapping("{licenseId}")
    fun updateLicenses(@PathVariable licenseId: String, @RequestBody license: License) = licenseService.update(license)

    @DeleteMapping("{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLicenses(@PathVariable licenseId: String) = licenseService.delete(licenseId)

}