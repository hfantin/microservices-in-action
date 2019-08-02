package com.github.hfantin.licensingservice.clients

import com.github.hfantin.licensingservice.model.Organization
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("organizationservice")
/**
 *
 * otes from Microservices in Action Book(page 118:
 *
 * When you use the standard Spring RestTemplate class, all service calls’ HTTP status
 * codes will be returned via the ResponseEntity class’s getStatusCode()
 * method. With the Feign Client, any HTTP 4xx – 5xx status codes returned by the service
 * being called will be mapped to a FeignException. The FeignException will
 * contain a JSON body that can be parsed for the specific error message.
 * Feign does provide you the ability to write an error decoder class that will map the
 * error back to a custom Exception class. Writing this decoder is outside the scope
 * of this book, but you can find examples of this in the Feign GitHub repository at
 * (https://github.com/Netflix/feign/wiki/Custom-error-handling).
 *
 */
interface OrganizationFeingClient {

    @GetMapping("/v1/organizations/{organizationId}", consumes = ["application/json"])
    fun getOrganization(@PathVariable organizationId: String): Organization?

}