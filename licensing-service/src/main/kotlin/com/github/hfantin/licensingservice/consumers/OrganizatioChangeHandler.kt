package com.github.hfantin.licensingservice.consumers

import com.github.hfantin.licensingservice.events.models.OrganizationChangeModel
import com.github.hfantin.licensingservice.repository.OrganizationRedisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener

@EnableBinding(CustomChannel::class)
class OrganizatioChangeHandler {

    private var logger = LoggerFactory.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var organizationRedisRepository: OrganizationRedisRepository

    @StreamListener("inboundOrgChanges")
    fun loggerSink(org: OrganizationChangeModel) {
        logger.info("organizacao mudou: $org")
        when (org.action) {
            "GET" -> logger.info("Received a GET event from the organization service for organization id {}", org.organizationId)
            "SAVE" -> logger.info("Received a SAVE event from the organization service for organization id {}", org.organizationId)
            "UPDATE" -> {
                logger.info("Received a UPDATE event from the organization service for organization id {}", org.organizationId)
                org.organizationId?.let { organizationRedisRepository.deleteOrganization(it) }

            }
            "DELETE" -> {
                logger.info("Received a DELETE event from the organization service for organization id {}", org.organizationId)
                org.organizationId?.let { organizationRedisRepository.deleteOrganization(it) }
            }
            else -> logger.error("Received an UNKNOWN event from the organization service of type {}", org.type)
        }

    }
}