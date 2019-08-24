package com.github.hfantin.authenticationservice.security

import com.github.hfantin.authenticationservice.config.ServiceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class JWTTokenStoreConfig {

    @Autowired
    private lateinit var serviceConfig: ServiceConfig

    @Bean
    fun tokenStore() = JwtTokenStore(jwtAccessTokenConverter())


    @Bean
    @Primary
    fun tokenServices() = DefaultTokenServices().apply {
        setTokenStore(tokenStore())
        setSupportRefreshToken(true)
    }

    @Bean
    fun jwtAccessTokenConverter() = JwtAccessTokenConverter().apply {
        setSigningKey(serviceConfig.jwtSigningKey)
    }

    @Bean
    fun jwtTokenEnhancer() = JWTTokenEnhancer()

}