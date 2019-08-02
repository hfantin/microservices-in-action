package com.github.hfantin.organizationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
