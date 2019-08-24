package com.github.hfantin.licensingservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServiceConfig {

//    @Value("\${example.property}")
//    lateinit var exampleProperty: String
//
//    @Value("\${redis.server}")
//    lateinit var  redisServer: String
//
//    @Value("\${redis.port}")
//    lateinit var  redisPort: String

    @Value("\${signing.key}")
    lateinit var  jwtSigningKey: String


}