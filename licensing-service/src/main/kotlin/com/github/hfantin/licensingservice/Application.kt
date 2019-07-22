package com.github.hfantin.licensingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LicensingServiceApplication

fun main(args: Array<String>) {
	runApplication<LicensingServiceApplication>(*args)
}
