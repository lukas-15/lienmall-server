package com.payap.lienmallserver.buyers.repositories

import com.payap.lienmallserver.authentications.models.tokens.Tokens
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Tokens, Long> {
    fun findByToken(token: String): Tokens?
}