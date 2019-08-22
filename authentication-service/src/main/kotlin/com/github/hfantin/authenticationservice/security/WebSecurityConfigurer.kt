package com.github.hfantin.authenticationservice.security

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails


@EnableWebSecurity
class WebSecurityConfigurer : WebSecurityConfigurerAdapter() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean() = super.authenticationManagerBean()

    @Bean
    public override fun userDetailsService() = InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder().username("hfantin").password("123456").roles("USER").build(),
            User.withDefaultPasswordEncoder().username("mari").password("123456").roles("USER", "ADMIN").build())


    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationEventPublisher(object : AuthenticationEventPublisher {
            override fun publishAuthenticationSuccess(authentication: Authentication) = log.info("Token request by password grant succeeded.")
            override fun publishAuthenticationFailure(exception: AuthenticationException, authentication: Authentication) = log.info("Token request by password grant failed: {}", exception)
        }).userDetailsService(userDetailsService())
    }
}