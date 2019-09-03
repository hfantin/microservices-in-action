package com.github.hfantin.organizationservice.controllers


import com.github.hfantin.organizationservice.model.Organization
import com.github.hfantin.organizationservice.services.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/organizations")
class OrganizationServiceController {

    @Autowired
    private lateinit var organizationService: OrganizationService

    @GetMapping("/{organizationId}")
    fun getOrganization(@PathVariable("organizationId") organizationId: String) = organizationService.get(organizationId).get()

    @PostMapping
    fun saveOrganization(@RequestBody organization: Organization) = organizationService.save(organization)

    @PutMapping("/{organizationId}")
    fun updateOrganization(@PathVariable organizationId: String, @RequestBody organization: Organization) {
        organization.id = organizationId
        organizationService.update(organization)
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrganization(@PathVariable organizationId: String) = organizationService.delete(organizationId)

}
