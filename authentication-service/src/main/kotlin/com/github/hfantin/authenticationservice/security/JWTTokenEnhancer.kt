package com.github.hfantin.authenticationservice.security

import com.github.hfantin.authenticationservice.repository.OrgUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import java.util.HashMap

class JWTTokenEnhancer : TokenEnhancer {
    @Autowired
    private lateinit var orgUserRepo: OrgUserRepository

    private fun getOrgId(userName: String) = orgUserRepo.findByUserName(userName).organizationid

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val additionalInfo: HashMap<String, Any> = hashMapOf("organizationId" to getOrgId(authentication.name))
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }
}