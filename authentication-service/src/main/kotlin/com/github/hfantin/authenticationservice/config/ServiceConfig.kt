package com.github.hfantin.authenticationservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@Configuration
class ServiceConfig {
    @Value("\${signing.key}")
    val jwtSigningKey = ""

}