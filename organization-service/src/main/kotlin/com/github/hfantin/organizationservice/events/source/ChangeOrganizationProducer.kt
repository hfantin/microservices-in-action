package com.github.hfantin.organizationservice.events.source

import com.github.hfantin.organizationservice.model.OrganizationChangeDto
import org.slf4j.LoggerFactory
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class ChangeOrganizationProducer {

    private var logger = LoggerFactory.getLogger(javaClass.simpleName)

    fun send(msg: OrganizationChangeDto, changeOrganizationSource: ChangeOrganizationSource): Boolean {
        val message = MessageBuilder.withPayload("${msg.action}:${msg.id}").build()
        logger.info("sending message $msg - message=$message")
        return changeOrganizationSource.sendChangeOrganization().send(message)
    }

}