package com.payap.lienmallserver.authentications.sign.dto.api

import com.payap.lienmallserver.authentications.models.UserTokens
import com.payap.lienmallserver.buyers.dto.api.BuyerResponseBodyDto

class SignResponseBodyDto(token: UserTokens) {
    val uuid = token.uuid
    val accessToken = token.accessToken.token
    val refreshToken = token.refreshToken.token

    val buyer = BuyerResponseBodyDto(token.buyer)
}