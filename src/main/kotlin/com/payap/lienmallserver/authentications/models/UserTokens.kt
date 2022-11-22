package com.payap.lienmallserver.authentications.models

import com.payap.lienmallserver.authentications.models.tokens.AccessTokens
import com.payap.lienmallserver.authentications.models.tokens.RefreshTokens
import com.payap.lienmallserver.buyers.models.Buyers
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
class UserTokens(buyers: Buyers, accessToken: AccessTokens, refreshToken: RefreshTokens) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(updatable = false, unique = true, nullable = false)
    @Type(type = "uuid-char")
    var uuid: UUID = UUID.randomUUID()

    @OneToOne
    var accessToken: AccessTokens = accessToken

    @OneToOne
    var refreshToken: RefreshTokens = refreshToken

    @ManyToOne(fetch = FetchType.LAZY)
    var buyer: Buyers = buyers
}