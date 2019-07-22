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

    @RequestMapping("/{licenseId}", method = [RequestMethod.GET])
    fun getLicence(@PathVariable("organizationId") organizationId: String,
                    @PathVariable("licenseId")
                    licenseId: String) = licenceRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)

    @RequestMapping(method = [RequestMethod.GET])
    fun getLicencesByOrganizationId(@PathVariable("organizationId") organizationId: String)
            = licenceRepository.findByOrganizationId(organizationId)


    @RequestMapping("{licenseId}", method = [RequestMethod.PUT])
    fun updateLicenses(@PathVariable("licenseId") licenseId: String) = "This is the put"


    @RequestMapping("{licenseId}", method = [RequestMethod.POST])
    fun saveLicenses(@PathVariable("licenseId") licenseId: String) = "This is the post"

    @RequestMapping("{licenseId}", method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLicenses(@PathVariable("licenseId") licenseId: String) = "This is the Delete"

}