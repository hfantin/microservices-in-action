package com.github.hfantin.zipkinsrv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import zipkin2.server.internal.EnableZipkinServer

@SpringBootApplication
@EnableZipkinServer
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
