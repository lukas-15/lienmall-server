package com.payap.lienmallserver.authentications.models.tokens

import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
abstract class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    @Column(updatable = false, unique = true, nullable = false)
    @Type(type = "uuid-char")
    open val uuid: UUID = UUID.randomUUID()

    @Column(nullable = false)
    open val token: String = generateToken()

    @Column(nullable = false)
    open val isActivated: Boolean = true

    @Column()
    open val expiredAt: LocalDateTime? = setExpiredAt()

    abstract fun setExpiredAt(): LocalDateTime

    fun isValid(): Boolean {
        return isActivated && !isExpired()
    }

    private fun isExpired(): Boolean {
        return LocalDateTime.now().isAfter(expiredAt)
    }

    private fun generateToken(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..64)
                .map { charset.random() }
                .joinToString("")
    }
}