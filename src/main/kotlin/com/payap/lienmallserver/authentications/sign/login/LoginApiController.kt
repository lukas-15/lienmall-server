package com.payap.lienmallserver.authentications.sign.login

import com.payap.lienmallserver.authentications.services.BuyerService
import com.payap.lienmallserver.authentications.sign.dto.api.SignResponseBodyDto
import com.payap.lienmallserver.authentications.sign.login.dto.api.LoginRequestBodyDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginApiController(private val buyerService: BuyerService) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequestBodyDto: LoginRequestBodyDto): SignResponseBodyDto {
        val token = buyerService.login(loginRequestBodyDto.email, loginRequestBodyDto.password)
        return SignResponseBodyDto(token)
    }
}