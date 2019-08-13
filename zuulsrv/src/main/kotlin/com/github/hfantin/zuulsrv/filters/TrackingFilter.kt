package com.github.hfantin.zuulsrv.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TrackingFilter : ZuulFilter() {

    @Autowired
    private lateinit var filterUtils: FilterUtils

    private fun isCorrelationIdPresent() = filterUtils.getCorrelationId() != null

    override fun filterType() = FilterUtils.PRE_FILTER_TYPE

    override fun filterOrder() = FILTER_ORDER

    override fun shouldFilter() = SHOULD_FILTER

    private fun generateCorrelationId() = java.util.UUID.randomUUID().toString()

    override fun run(): Any? {
        if (isCorrelationIdPresent()) {
            logger.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId())
        } else {
            filterUtils.setCorrelationId(generateCorrelationId())
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId())
        }

        val ctx = RequestContext.getCurrentContext()
        logger.debug("Processing incoming request for {}.", ctx.request.requestURI)
        return null
    }

    companion object {
        private val FILTER_ORDER = 1
        private val SHOULD_FILTER = true
        private val logger = LoggerFactory.getLogger(TrackingFilter::class.java)
    }
}