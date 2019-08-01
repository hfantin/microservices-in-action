package com.github.hfantin.licensingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
