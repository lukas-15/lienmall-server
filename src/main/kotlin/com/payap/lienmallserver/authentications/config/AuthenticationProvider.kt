package com.payap.lienmallserver.authentications.config

import com.payap.lienmallserver.authentications.services.UserTokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component


// https://gksdudrb922.tistory.com/217
@Component
class AuthenticationProvider(private val userTokenService: UserTokenService) {
    fun getAuthentication(accessToken: String): Authentication? {
        if (!userTokenService.validateToken(accessToken)) {
            return null
        }

        val authorities = listOf("USER")
                .map { role -> SimpleGrantedAuthority("ROLE_$role") }
        val userToken = userTokenService.getByAccessToken(accessToken)!!
        return UsernamePasswordAuthenticationToken(userToken.buyer, "", authorities)
    }
}