package com.github.hfantin.zuulsrv.filters


import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * this is the post filter
 */
@Component
class ResponseFilter : ZuulFilter() {

    @Autowired
    private lateinit var filterUtils: FilterUtils

    override fun filterType() = FilterUtils.POST_FILTER_TYPE

    override fun filterOrder() = FILTER_ORDER

    override fun shouldFilter() = SHOULD_FILTER

    override fun run() = with(RequestContext.getCurrentContext()) {
        logger.info("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId())
        response.addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId())
        logger.info("Completing outgoing request for {}.", request.requestURI)
    }

    companion object {
        private val FILTER_ORDER = 1
        private val SHOULD_FILTER = true
        private val logger = LoggerFactory.getLogger(ResponseFilter::class.java)
    }
}
