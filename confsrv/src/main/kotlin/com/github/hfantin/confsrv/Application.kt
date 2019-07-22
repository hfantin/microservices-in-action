package com.github.hfantin.confsrv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfsrvApplication

fun main(args: Array<String>) {
	runApplication<ConfsrvApplication>(*args)
}
