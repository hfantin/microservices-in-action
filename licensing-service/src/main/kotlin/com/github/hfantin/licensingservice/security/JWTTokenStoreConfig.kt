package com.github.hfantin.licensingservice.security

import com.github.hfantin.licensingservice.config.ServiceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class JWTTokenStoreConfig {

    @Autowired
    private lateinit var serviceConfig: ServiceConfig

    //JWT
    @Bean
    fun tokenStore() = JwtTokenStore(jwtAccessTokenConverter())

    //JWT
    @Bean
    @Primary
    fun tokenServices() = DefaultTokenServices().apply {
        setTokenStore(tokenStore())
        setSupportRefreshToken(true)
    }


    //JWT
    @Bean
    fun jwtAccessTokenConverter() = JwtAccessTokenConverter().apply {
        setSigningKey(serviceConfig.jwtSigningKey)
    }

}