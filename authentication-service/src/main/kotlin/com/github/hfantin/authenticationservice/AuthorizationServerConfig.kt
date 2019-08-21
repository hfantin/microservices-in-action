package com.github.hfantin.authenticationservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.stereotype.Component


@Component
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        val passwordEncoder = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()

        // @formatter:off
        clients.inMemory()
                .withClient("authtest")
                .authorizedGrantTypes("password")
                .secret(passwordEncoder.encode("authtest"))
                .scopes("any")
        // @formatter:on
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        // @formatter:off
        endpoints
                .authenticationManager(this.authenticationManager)
        // @formatter:on
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        // @formatter:off
        security
                .checkTokenAccess("isAuthenticated()")
        // @formatter:on
    }

}