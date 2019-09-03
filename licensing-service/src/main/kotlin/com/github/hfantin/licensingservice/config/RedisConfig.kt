package com.github.hfantin.licensingservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value("\${spring.redis.server}")
    private lateinit var server: String

    @Value("\${spring.redis.port}")
    private var port: Int = 0

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(server, port)

    @Bean
    fun redisTemplate() = RedisTemplate<String, Any>().apply {
        keySerializer = StringRedisSerializer()
        hashKeySerializer = GenericToStringSerializer<Any>(Any::class.java)
        hashValueSerializer = JdkSerializationRedisSerializer()
        valueSerializer = JdkSerializationRedisSerializer()
        this.setConnectionFactory(redisConnectionFactory())
    }


}