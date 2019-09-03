package com.github.hfantin.licensingservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory

@Configuration
class RedisConfig {

    @Value("\${redis.host}")
    private lateinit var server: String

    @Value("\${redis.port}")
    private var port: Int = 0
    
    @Bean
    fun redisConnectionFactory() = JedisConnectionFactory(RedisStandaloneConfiguration(server, port))

}