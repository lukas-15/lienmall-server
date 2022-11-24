package com.payap.lienmallserver.authentications.sign.token

import com.payap.lienmallserver.authentications.services.UserTokenService
import com.payap.lienmallserver.authentications.sign.dto.api.SignResponseBodyDto
import com.payap.lienmallserver.authentications.sign.token.dto.api.RefreshTokenRequestBodyDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TokenApiController(private val userTokenService: UserTokenService) {
    @PostMapping("/token")
    fun refreshToken(@RequestBody refreshTokenRequestBodyDto: RefreshTokenRequestBodyDto): SignResponseBodyDto {
        val token = userTokenService.refreshAccessToken(refreshTokenRequestBodyDto.refreshToken)
        return SignResponseBodyDto(token)
    }
}