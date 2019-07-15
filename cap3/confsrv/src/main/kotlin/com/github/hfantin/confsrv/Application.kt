package com.github.hfantin.confsrv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfsrvApplication

fun main(args: Array<String>) {
	runApplication<ConfsrvApplication>(*args)
}
