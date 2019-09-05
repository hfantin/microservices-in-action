package com.github.hfantin.zuulsrv.utils

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * This is a Servlet Filter who intercepts all incoming http requests comming
 * into the service and map the correlation ID
 */
@Component
class UserContextFilter : Filter {

    companion object {
        private val logger = LoggerFactory.getLogger(UserContextFilter::class.java)
    }


    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest
        with(UserContextHolder.getContext()) {
            this.correlationId = getHeader(httpServletRequest, UserContextType.CORRELATION_ID.key)
            this.userId = getHeader(httpServletRequest, UserContextType.USER_ID.key)
            this.authToken = getHeader(httpServletRequest, UserContextType.AUTH_TOKEN.key)
            this.organizationId = getHeader(httpServletRequest, UserContextType.ORG_ID.key)
            logger.info("UserContextFilter - keys: {}", this)
        }
        chain?.doFilter(httpServletRequest, response)
    }

    private fun getHeader(request: HttpServletRequest, id: String) = request.getHeader(id) ?: ""
}