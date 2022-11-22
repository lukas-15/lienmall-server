package com.payap.lienmallserver.authentications.sign.signup

import com.payap.lienmallserver.authentications.services.BuyerService
import com.payap.lienmallserver.authentications.sign.dto.api.SignResponseBodyDto
import com.payap.lienmallserver.authentications.sign.signup.dto.api.SignupRequestBodyDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SignupApiController(private val buyerService: BuyerService) {
    @PostMapping("/signup")
    fun signup(@RequestBody signupRequestBodyDto: SignupRequestBodyDto): SignResponseBodyDto {
        val token = buyerService.signup(signupRequestBodyDto.toBuyer())
        return SignResponseBodyDto(token)
    }

    @GetMapping("/test")
    fun test(): String {

        return "test"
    }
}