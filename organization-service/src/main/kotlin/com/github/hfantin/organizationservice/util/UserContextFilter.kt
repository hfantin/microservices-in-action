package com.github.hfantin.organizationservice.util

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class UserContextFilter : Filter {

    private val logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest
        with(UserContextHolder.getContext()) {
            this.correlationId = getHeader(httpServletRequest, UserContextType.CORRELATION_ID.key)
            this.userId = getHeader(httpServletRequest, UserContextType.USER_ID.key)
            this.authToken = getHeader(httpServletRequest, UserContextType.AUTH_TOKEN.key)
            this.organizationId = getHeader(httpServletRequest, UserContextType.ORG_ID.key)
            logger.info("UserContextFilter = keys: {}", this)
        }
        chain?.doFilter(httpServletRequest, response)
    }

    private fun getHeader(request: HttpServletRequest, id: String) = request.getHeader(id) ?: ""
}