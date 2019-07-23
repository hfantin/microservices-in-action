package com.github.hfantin.organizationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class OrganizationServiceApplication

fun main(args: Array<String>) {
	runApplication<OrganizationServiceApplication>(*args)
}
