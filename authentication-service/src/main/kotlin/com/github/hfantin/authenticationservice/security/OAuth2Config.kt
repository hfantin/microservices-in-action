package com.github.hfantin.authenticationservice.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.stereotype.Component


@Component
@EnableAuthorizationServer
class OAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        clients.inMemory()
                .withClient("authtest")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .secret(passwordEncoder.encode("123456"))
                .scopes("webclient", "mobileclient", "any")

    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.checkTokenAccess("isAuthenticated()")
    }

}