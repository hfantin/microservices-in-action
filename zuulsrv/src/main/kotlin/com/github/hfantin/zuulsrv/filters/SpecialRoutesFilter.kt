package com.github.hfantin.zuulsrv.filters


import com.github.hfantin.zuulsrv.model.AbTestingRoute
import com.github.hfantin.zuulsrv.utils.UserContextFilter
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.apache.http.Header
import org.apache.http.HttpHost
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPatch
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.ContentType
import org.apache.http.entity.InputStreamEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicHttpRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import javax.servlet.http.HttpServletRequest
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.ArrayList
import java.util.Random
import javax.servlet.ServletInputStream

/**
 * this is a dynamic route filter, normaly used to do A/B testing.
 * this class simulates rolling out a new version of the organization service where you want 50%
 * of the users go to the old service and 50% of the users to go to the new service.
 */
@Component
class SpecialRoutesFilter : ZuulFilter() {

    @Autowired
    private lateinit var filterUtils: FilterUtils

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private val helper = ProxyRequestHelper(ZuulProperties())

    override fun filterType() = FilterUtils.ROUTE_FILTER_TYPE

    override fun filterOrder() = FILTER_ORDER

    override fun shouldFilter() = SHOULD_FILTER

    private fun getAbRoutingInfo(serviceName: String): AbTestingRoute? {
        var restExchange: ResponseEntity<AbTestingRoute>? = null
        try {
            restExchange = restTemplate.exchange("http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET, null, AbTestingRoute::class.java, serviceName)
        } catch (ex: HttpClientErrorException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) return null
            throw ex
        }

        return restExchange.body
    }

    private fun buildRouteString(oldEndpoint: String, newEndpoint: String, serviceName: String): String {
        val index = oldEndpoint.indexOf(serviceName)
        val strippedRoute = oldEndpoint.substring(index + serviceName.length)
        logger.info("Target route: $newEndpoint/$strippedRoute")
        return "$newEndpoint/$strippedRoute"
    }

    private fun getVerb(request: HttpServletRequest) = request.method.toUpperCase()

    private fun getHttpHost(host: URL) = HttpHost(host.host, host.port, host.protocol)

    private fun convertHeaders(headers: MultiValueMap<String, String>): Array<Header> {
        val list = ArrayList<Header>()
        headers.keys.forEach { name ->
            headers[name]?.forEach { value ->
                list.add(BasicHeader(name, value))
            }
        }
        return list.toTypedArray()
    }

    @Throws(IOException::class)
    private fun forwardRequest(httpclient: HttpClient, httpHost: HttpHost, httpRequest: HttpRequest) = httpclient.execute(httpHost, httpRequest)

    private fun revertHeaders(headers: Array<Header>): MultiValueMap<String, String> {
        val map = LinkedMultiValueMap<String, String>()
        headers.forEach { header ->
            val name = header.name
            if (!map.containsKey(name)) {
                map[name] = ArrayList()
            }
            map[name]?.add(header.value)
        }
        return map
    }

    private fun getRequestBody(request: HttpServletRequest): InputStream? = try {
        request.inputStream
    } catch (ex: IOException) {
        // no requestBody is ok.
        null
    }

    @Throws(IOException::class)
    private fun setResponse(response: HttpResponse) {
        this.helper.setResponse(response.statusLine.statusCode, response.entity?.content, revertHeaders(response.allHeaders))
    }

    @Throws(Exception::class)
    private fun forward(httpclient: HttpClient?, verb: String, uri: String,
                        request: HttpServletRequest, headers: MultiValueMap<String, String>,
                        params: MultiValueMap<String, String>, requestEntity: InputStream?): HttpResponse {
        val info = this.helper.debug(verb, uri, headers, params, requestEntity)
        val host = URL(uri)
        val httpHost = getHttpHost(host)
        val httpRequest: HttpRequest
        val contentLength = request.contentLength
        val entity = InputStreamEntity(requestEntity!!, contentLength.toLong(),
                request.contentType?.let { ContentType.create(request.contentType) })
        when (verb.toUpperCase()) {
            "POST" -> {
                val httpPost = HttpPost(uri)
                httpRequest = httpPost
                httpPost.entity = entity
            }
            "PUT" -> {
                val httpPut = HttpPut(uri)
                httpRequest = httpPut
                httpPut.entity = entity
            }
            "PATCH" -> {
                val httpPatch = HttpPatch(uri)
                httpRequest = httpPatch
                httpPatch.entity = entity
            }
            else -> httpRequest = BasicHttpRequest(verb, uri)
        }
        try {
            httpRequest.setHeaders(convertHeaders(headers))

            return forwardRequest(httpclient!!, httpHost, httpRequest)
        } finally {
        }
    }


    fun useSpecialRoute(testRoute: AbTestingRoute): Boolean {
        if (testRoute.active == "N") return false
        val value = Random().nextInt(10) + 1
        return testRoute.weight!! < value

    }

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()

        // simulates A/B test
        val abTestRoute = getAbRoutingInfo(filterUtils.getServiceId())
        abTestRoute?.let {
            if (useSpecialRoute(abTestRoute)) {
                val route = buildRouteString(ctx.request.requestURI, it.endpoint, ctx["serviceId"].toString())
                forwardToSpecialRoute(route)
            }
        }
        return null
    }

    private fun forwardToSpecialRoute(route: String) {
        val context = RequestContext.getCurrentContext()
        val request = context.request

        val headers = this.helper.buildZuulRequestHeaders(request)
        val params = this.helper.buildZuulRequestQueryParams(request)
        val verb = getVerb(request)
        val requestEntity = getRequestBody(request)
        if (request.contentLength < 0) {
            context.setChunkedRequestBody()
        }
        this.helper.addIgnoredHeaders()
//        var httpClient: CloseableHttpClient? = null
//        var response: HttpResponse? = null
        try{
            HttpClients.createDefault().use { httpClient ->
                val response: HttpResponse = forward(httpClient, verb, route, request, headers, params, requestEntity)
                setResponse(response)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
//        try {
//            httpClient = HttpClients.createDefault()
//            response = forward(httpClient, verb, route, request, headers, params, requestEntity)
//            setResponse(response)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        } finally {
//            try {
//                httpClient?.close()
//            } catch (ex: IOException) {
//            }
//
//        }
    }

    companion object {
        private val FILTER_ORDER = 1
        private val SHOULD_FILTER = true
        private val logger = LoggerFactory.getLogger(SpecialRoutesFilter::class.java)
    }
}
