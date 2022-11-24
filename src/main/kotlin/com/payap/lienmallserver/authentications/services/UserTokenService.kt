package com.payap.lienmallserver.authentications.services

import com.payap.lienmallserver.authentications.exceptions.ExpiredTokenExceptions
import com.payap.lienmallserver.authentications.models.UserTokens
import com.payap.lienmallserver.authentications.models.tokens.AccessTokens
import com.payap.lienmallserver.authentications.models.tokens.RefreshTokens
import com.payap.lienmallserver.authentications.repositories.UserTokenRepository
import com.payap.lienmallserver.buyers.models.Buyers
import com.payap.lienmallserver.buyers.repositories.TokenRepository
import com.payap.lienmallserver.common.exceptions.NotFoundExceptions
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTokenService(
    private val userTokenRepository: UserTokenRepository,
    private val tokenRepository: TokenRepository
) {

    fun create(buyer: Buyers): UserTokens {
        val accessToken = createAccessToken()
        val refreshToken = createRefreshToken()

        val userToken = UserTokens(buyer, accessToken, refreshToken)
        return userTokenRepository.save(userToken)
    }

    private fun createAccessToken(): AccessTokens {
        val accessToken = AccessTokens()
        return tokenRepository.save(accessToken)
    }

    private fun createRefreshToken(): RefreshTokens {
        val refreshToken = RefreshTokens()
        return tokenRepository.save(refreshToken)
    }

    fun validateToken(token: String): Boolean {
        val persistedToken = tokenRepository.findByToken(token)
        return persistedToken
            ?.isValid()
            ?: false
    }

    fun getByAccessToken(accessToken: String): UserTokens? {
        return userTokenRepository.findByAccessToken_Token(accessToken)
    }

    @Transactional
    fun refreshAccessToken(refreshTokenString: String): UserTokens {
        val userToken = userTokenRepository.findByRefreshToken_Token(refreshTokenString)
            ?: throw NotFoundExceptions("Not found refresh token")
        val refreshToken = userToken.refreshToken

        if (!refreshToken.isValid()) {
            throw ExpiredTokenExceptions("Expired refresh token")
        }

        disableAccessToken(userToken.accessToken)
        userToken.accessToken = createAccessToken()
        return userTokenRepository.save(userToken)
    }

    private fun disableAccessToken(accessToken: AccessTokens): AccessTokens {
        accessToken.disable()
        return tokenRepository.save(accessToken)
    }

}