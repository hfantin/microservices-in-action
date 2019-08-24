package com.github.hfantin.zuulsrv.filters

import com.github.hfantin.zuulsrv.config.ServiceConfig
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import io.jsonwebtoken.Jwts
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.nio.charset.Charset

/**
 * this is the pre filter
 */
@Component
class TrackingFilter : ZuulFilter() {

    @Autowired
    private lateinit var filterUtils: FilterUtils

    @Autowired
    private lateinit var serviceConfig: ServiceConfig

    private fun isCorrelationIdPresent() = filterUtils.getCorrelationId() != null

    override fun filterType() = FilterUtils.PRE_FILTER_TYPE

    override fun filterOrder() = FILTER_ORDER

    override fun shouldFilter() = SHOULD_FILTER

    private fun generateCorrelationId() = java.util.UUID.randomUUID().toString()

    private fun getOrganizationId(): String {
        var result = ""
        filterUtils.getAuthToken()?.let {
            val token = it.substringAfterLast("Bearer ")
            logger.info("getOrganizationId() - authToken: {}. ", token)
            try {
                val claims = Jwts.parser()
                        .setSigningKey(serviceConfig.jwtSigningKey.toByteArray(Charsets.UTF_8))
                        .parseClaimsJws(token).body
                result = "${claims["organizationId"]}"
                logger.info("gettting organizationId: {}. ", result)
            } catch (e: Exception) {
                logger.error("", e)
            }
        }
        return result
    }

    override fun run(): Any? {
        if (isCorrelationIdPresent()) {
            logger.info("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId())
        } else {
            filterUtils.setCorrelationId(generateCorrelationId())
            logger.info("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId())
        }

        val ctx = RequestContext.getCurrentContext()


        filterUtils.setOrgId(getOrganizationId())
        logger.info("The organization id from the token is : " + filterUtils.getOrgId())

        logger.info("Processing incoming request for {}.", ctx.request.requestURI)
        return null
    }

    companion object {
        private val FILTER_ORDER = 1
        private val SHOULD_FILTER = true
        private val logger = LoggerFactory.getLogger(TrackingFilter::class.java)
    }
}