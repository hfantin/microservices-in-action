package com.github.hfantin.zuulsrv.utils

import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

/**
 * This class is used to inject the correlation ID into any outgoing
 * HTTP based service requests being executed from a RestTemplate instance.
 * This is done to ensure that you can establish a linkage between service calls.
 * This is used in RestTemplateConfig
 */
class UserContextInterceptor: ClientHttpRequestInterceptor {

    private val logger = LoggerFactory.getLogger(UserContextInterceptor::class.java)

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        with(request.headers){
            this.add(UserContextType.CORRELATION_ID.key, UserContextHolder.getContext().correlationId)
            this.add(UserContextType.AUTH_TOKEN.key, UserContextHolder.getContext().authToken)
        }
        logger.info("UserContextInterceptor - headers: ${request.headers}")
        return execution.execute(request, body)
    }
}