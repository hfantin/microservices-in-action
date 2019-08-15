package com.github.hfantin.authenticationservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

/**
 * this class defines what applications are registered with your OAuth2 authentication service.
 * It’s important to note that just because an application is registered with your OAuth2 service,
 * it doesn’t mean that the service will have access to any protected resources.
 *
 * Authentication vs Authorization:
 * Authentication is the act of a user proving who they are by providing credentials.
 * Authorization determines whether a user is allowed to do what they’re trying to do.
 */
@Configuration
class OAuthConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        // this method supports 2 diferent types of stores: in memory and jdbc.
        // to keep this example simple, we will use in memory
        clients?.inMemory()
                ?.withClient("eagleeye")
                ?.secret("thisissecret")
                ?.authorizedGrantTypes("refresh_token", "password", "client_credentials")
                ?.scopes("webclient", "mobileclient")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints?.authenticationManager(authenticationManager)
                ?.userDetailsService(userDetailsService)
    }
}