package com.github.hfantin.authenticationservice.controllers

import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@EnableResourceServer
class UserController {

    @RequestMapping(value = ["/user"], produces = ["application/json"])
    fun user(user: OAuth2Authentication): Map<String, Any> {
        val userInfo = HashMap<String, Any>()
        userInfo["user"] = user.userAuthentication.principal
        userInfo["authorities"] = AuthorityUtils.authorityListToSet(user.userAuthentication.authorities)
        return userInfo
    }
}
