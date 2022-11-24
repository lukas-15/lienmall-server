package com.payap.lienmallserver.authentications.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

// https://velog.io/@tmdgh0221/Spring-Security-%EC%99%80-OAuth-2.0-%EC%99%80-JWT-%EC%9D%98-%EC%BD%9C%EB%9D%BC%EB%B3%B4#%EC%84%9C%EB%B2%84-%EA%B8%B0%EB%B0%98-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig(private val authenticationProvider: AuthenticationProvider) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .antMatchers("/api/login", "/api/signup", "/api/token").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                AuthenticationFilter(authenticationProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}