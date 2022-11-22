package com.payap.lienmallserver.authentications.sign.signup.dto.api

import com.payap.lienmallserver.buyers.models.Buyers

class SignupRequestBodyDto(val password: String, val email: String, val name: String) {
    fun toBuyer(): Buyers {
        return Buyers(email = email, password = password, name = name)
    }
}