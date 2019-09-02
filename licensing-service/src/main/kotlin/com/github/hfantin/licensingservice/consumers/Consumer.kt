package com.github.hfantin.licensingservice.consumers

import com.github.hfantin.licensingservice.model.OrganizationChangeDto
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

@EnableBinding(Sink::class)
class Consumer {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
    @StreamListener(Sink.INPUT)
    fun loggerSink(dto: OrganizationChangeDto)
    {
        logger.debug("Received an event for organization id {}", dto.id);
    }
}

