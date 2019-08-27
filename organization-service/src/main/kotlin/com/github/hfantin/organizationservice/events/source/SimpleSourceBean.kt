package com.github.hfantin.organizationservice.events.source

import com.github.hfantin.organizationservice.events.models.OrganizationChangeModel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SimpleSourceBean {
    
    private val logger = LoggerFactory.getLogger(SimpleSourceBean::class.java)

    private lateinit var source: Source

    @Autowired
    fun SimpleSourceBean(source: Source) {
        this.source = source
    }

    fun publishOrgChange(action: String, orgId: String) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, orgId)
        val change = OrganizationChangeModel(
                OrganizationChangeModel::class.java.typeName,
                action,
                orgId,
                //UserContext.correlationId)
                "aaa")

        source.output().send(MessageBuilder.withPayload<Any>(change).build())
    }
    
    
}