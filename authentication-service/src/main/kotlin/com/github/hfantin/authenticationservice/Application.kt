package com.github.hfantin.authenticationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer


@SpringBootApplication
class Application


fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
