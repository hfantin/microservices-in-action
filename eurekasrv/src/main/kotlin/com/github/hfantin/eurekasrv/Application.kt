package com.github.hfantin.eurekasrv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekasrvApplication

fun main(args: Array<String>) {
	runApplication<EurekasrvApplication>(*args)
}
