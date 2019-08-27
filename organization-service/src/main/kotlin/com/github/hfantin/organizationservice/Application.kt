package com.github.hfantin.organizationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer


/**
 *
 * The use of Source.class in the @EnableBinding annotation tells Spring Cloud Stream that this service will
 * communicate with the message broker via a set of channels defined on the Source class.
 * Remember, channels sit above a message queue. Spring Cloud Stream has a default set of channels
 * that can be configured to speak to a message broker
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableResourceServer
@EnableBinding(Source::class)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
