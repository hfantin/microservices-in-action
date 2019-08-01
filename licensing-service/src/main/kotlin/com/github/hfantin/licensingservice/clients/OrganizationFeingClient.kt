package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("organizationservice")
interface OrganizationFeingClient {

    @GetMapping("/v1/organizations/{organizationId}", consumes = ["application/json"])
    fun getOrganization(@PathVariable organizationId: String): Organization?

}