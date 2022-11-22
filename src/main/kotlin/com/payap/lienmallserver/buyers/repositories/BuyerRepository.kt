package com.payap.lienmallserver.buyers.repositories

import com.payap.lienmallserver.buyers.models.Buyers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface BuyerRepository : JpaRepository<Buyers, Long> {
    fun findByEmail(email: String): Buyers

    @Modifying
    @Query("update Tokens t set t.isActivated = false where t.id in (select ut.accessToken.id from UserTokens ut where ut.buyer = :buyer)")
    fun disableAllAccessTokens(@Param("buyer") buyer: Buyers)

    @Modifying
    @Query("update Tokens t set t.isActivated = false where t.id in (select ut.refreshToken.id from UserTokens ut where ut.buyer = :buyer)")
    fun disableAllRefreshTokens(@Param("buyer") buyer: Buyers)
}