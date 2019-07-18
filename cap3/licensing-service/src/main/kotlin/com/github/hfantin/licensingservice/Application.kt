package com.github.hfantin.licensingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LicensingServiceApplication

fun main(args: Array<String>) {
	runApplication<LicensingServiceApplication>(*args)
}
