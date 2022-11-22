package com.payap.lienmallserver.authentications.config

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class AuthenticationFilter(private val authenticationProvider: AuthenticationProvider) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = getToken(request as HttpServletRequest)
        if (token.isPresent) {
            val authentication = authenticationProvider.getAuthentication(token.get())
            println("ttttest")
            println(authentication)

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        }

        chain.doFilter(request, response)
    }


    fun getToken(request: HttpServletRequest): Optional<String> {
        val authorizationToken = request.getHeader("Authorization")
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer")) {
            return Optional.of(authorizationToken.substring(7))
        }

        return Optional.empty()
    }

}