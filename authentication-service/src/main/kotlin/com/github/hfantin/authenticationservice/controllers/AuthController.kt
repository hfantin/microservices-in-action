package com.github.hfantin.authenticationservice.controllers

import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    //    @RequestMapping(value = ["/user"], produces = ["application/json"])
    @GetMapping("/user")
    fun user(user: OAuth2Authentication) = hashMapOf("user" to user.userAuthentication.principal, "authorities" to AuthorityUtils.authorityListToSet(user.userAuthentication.authorities))

}