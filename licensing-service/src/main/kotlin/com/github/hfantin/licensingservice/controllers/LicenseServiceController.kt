package com.github.hfantin.licensingservice.controllers

import com.github.hfantin.licensingservice.model.License
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
class LicenseServiceController {

    @RequestMapping("/{licenseId}", method = [RequestMethod.GET])
    fun getLicences(@PathVariable("organizationId") organizationId: String,
                    @PathVariable("licenseId")
                    licenseId: String) = License(licenseId, organizationId, "produto", "tipo")


    @RequestMapping("{licenseId}", method = [RequestMethod.PUT])
    fun updateLicenses(@PathVariable("licenseId") licenseId: String) = "This is the put"


    @RequestMapping("{licenseId}", method = [RequestMethod.POST])
    fun saveLicenses(@PathVariable("licenseId") licenseId: String) = "This is the post"

    @RequestMapping("{licenseId}", method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLicenses(@PathVariable("licenseId") licenseId: String) = "This is the Delete"

}