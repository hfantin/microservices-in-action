package com.github.hfantin.zipkinsrv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import zipkin.server.EnableZipkinServer

@SpringBootApplication
@EnableZipkinServer
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
