package com.github.hfantin.authenticationservice.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

@Configuration
class JWTOAuth2Config: AuthorizationServerConfigurerAdapter() {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var tokenServices: DefaultTokenServices

    @Autowired
    private lateinit var jwtAccessTokenConverter: JwtAccessTokenConverter

    @Autowired
    private lateinit var jwtTokenEnhancer: TokenEnhancer

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain().apply {
            setTokenEnhancers(listOf(jwtTokenEnhancer, jwtAccessTokenConverter))
        }

        endpoints.tokenStore(tokenStore)                             //JWT
                .accessTokenConverter(jwtAccessTokenConverter)       //JWT
                .tokenEnhancer(tokenEnhancerChain)                   //JWT
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }


    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        clients.inMemory()
                .withClient("authtest")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .secret(passwordEncoder.encode("123456"))
                .scopes("webclient", "mobileclient", "any")

    }

    /*@Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {

        clients.inMemory()
                .withClient("eagleeye")
                .secret("thisissecret")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("webclient", "mobileclient")
    }*/
}