package com.github.hfantin.licensingservice

import com.github.hfantin.licensingservice.events.models.OrganizationChangeModel
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableJpaAuditing
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
@EnableBinding(Sink::class)
class Application {
    private val logger = LoggerFactory.getLogger(Application::class.java)
    
    @StreamListener(Sink.INPUT)
    fun loggerSink(orgChange: OrganizationChangeModel)
    {
        logger.debug("Received an event for organization id {}", orgChange.organizationId);
    }
}


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
