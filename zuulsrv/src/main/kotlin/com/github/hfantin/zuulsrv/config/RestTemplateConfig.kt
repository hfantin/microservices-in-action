package com.github.hfantin.licensingservice.config

import com.github.hfantin.zuulsrv.filters.ResponseFilter
import com.github.hfantin.zuulsrv.utils.UserContextInterceptor
import org.slf4j.LoggerFactory
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.util.*


@Configuration
class RestTemplateConfig {

    /**
     * this is the restTemplate used by OrganizationRestTemplateClient and must use @LoadBalance annotation
     * to tells springcloud to create a ribbon backed RestTemplate class
     */
    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate().apply {
        logger.info("restTemplate() - interceptors=$interceptors")
        if (interceptors == null)
            interceptors = listOf(UserContextInterceptor())
        else
            interceptors.add(UserContextInterceptor())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RestTemplateConfig::class.java)
    }
}
