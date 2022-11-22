package com.payap.lienmallserver.authentications.repositories

import com.payap.lienmallserver.authentications.models.UserTokens
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserTokenRepository : JpaRepository<UserTokens, Long> {

    fun findByAccessToken_Token(token: String): UserTokens?

}