package com.github.hfantin.zuulsrv.filters

import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component

@Component
class FilterUtils {

    fun getCorrelationId() = getHeader(CORRELATION_ID)

    fun setCorrelationId(correlationId: String) = setHeaader(CORRELATION_ID, correlationId)

    fun getOrgId() = getHeader(ORG_ID)

    fun setOrgId(orgId: String) = setHeaader(ORG_ID, orgId)

    fun getUserId() = getHeader(USER_ID)

    fun setUserId(userId: String) = setHeaader(USER_ID, userId)

    fun getAuthToken() = with(RequestContext.getCurrentContext()) {
        request.getHeader(AUTH_TOKEN)
    }

    fun getServiceId() = with(RequestContext.getCurrentContext()) { get("serviceId")?.toString() ?: "" }

    private fun getHeader(name: String) = with(RequestContext.getCurrentContext()) {
        request.getHeader(name) ?: zuulRequestHeaders[name]
    }

    fun setHeaader(key: String, header: String) = with(RequestContext.getCurrentContext()) { addZuulRequestHeader(key, header) }

    companion object {
        val CORRELATION_ID = "tmx-correlation-id"
        val AUTH_TOKEN = "tmx-auth-token"
        val USER_ID = "tmx-user-id"
        val ORG_ID = "tmx-org-id"
        val PRE_FILTER_TYPE = "pre"
        val POST_FILTER_TYPE = "post"
        val ROUTE_FILTER_TYPE = "route"
    }


}
