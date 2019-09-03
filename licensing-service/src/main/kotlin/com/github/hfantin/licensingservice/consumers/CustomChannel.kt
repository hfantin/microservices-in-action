package com.github.hfantin.licensingservice.consumers

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface CustomChannel {

    @Input("inboundOrgChanges")
    fun orgs(): SubscribableChannel
}