package com.github.hfantin.organizationservice.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1")
class ServiceController {
    @Autowired
    private lateinit var discoveryClient: DiscoveryClient

    @RequestMapping("/services/{applicationName}")
    fun serviceInstancesByApplicationName(@PathVariable applicationName: String)=
            discoveryClient.getInstances(applicationName)
}