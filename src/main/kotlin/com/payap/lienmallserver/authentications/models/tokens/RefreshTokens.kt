package com.payap.lienmallserver.authentications.models.tokens

import java.time.LocalDateTime
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("REFRESH_TOKEN")
class RefreshTokens : Tokens() {
    override fun setExpiredAt(): LocalDateTime {
        return LocalDateTime.now().plusDays(2)
    }
}