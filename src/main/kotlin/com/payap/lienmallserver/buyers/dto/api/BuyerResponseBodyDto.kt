package com.payap.lienmallserver.buyers.dto.api

import com.payap.lienmallserver.buyers.models.Buyers

class BuyerResponseBodyDto(buyer: Buyers) {
    val uuid = buyer.uuid
    val email = buyer.email
    val name = buyer.name
}