package com.payap.lienmallserver.authentications.services

import com.payap.lienmallserver.authentications.models.UserTokens
import com.payap.lienmallserver.buyers.exceptions.FailIssueNewAuthenticationExceptions
import com.payap.lienmallserver.buyers.models.Buyers
import com.payap.lienmallserver.buyers.repositories.BuyerRepository
import com.payap.lienmallserver.common.exceptions.AlreadyExistExceptions
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BuyerService(
    private val buyerRepository: BuyerRepository,
    private val userTokenService: UserTokenService
) {

    @Transactional
    fun signup(buyer: Buyers): UserTokens {
        val persistedBuyer = try {
            buyerRepository.save(buyer)
        } catch (exception: RuntimeException) {
            throw AlreadyExistExceptions("Already exist buyer", exception)
        }

        return userTokenService.create(persistedBuyer)
    }

    @Transactional
    fun login(email: String, password: String): UserTokens {
        try {
            val persistedBuyer = buyerRepository.findByEmail(email)
            if (!persistedBuyer.equalsPassword(password)) {
                throw FailIssueNewAuthenticationExceptions("Fail to login")
            }

            disableAllTokens(persistedBuyer)
            return userTokenService.create(persistedBuyer)
        } catch (exception: RuntimeException) {
            throw FailIssueNewAuthenticationExceptions("Fail to issue new authentication", exception)
        }
    }

    private fun disableAllTokens(buyer: Buyers) {
        buyerRepository.disableAllAccessTokens(buyer)
        buyerRepository.disableAllRefreshTokens(buyer)
    }

}