package com.payap.lienmallserver.buyers.models

import com.payap.lienmallserver.authentications.models.UserTokens
import org.hibernate.annotations.Type
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import java.util.*
import javax.persistence.*

@Entity
class Buyers : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(updatable = false, unique = true, nullable = false)
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID()

    @Column
    var name: String? = null

    @Column(unique = true)
    var email: String? = null

    @Column
    var encryptedPassword: String? = null

    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
    var userTokens: List<UserTokens> = ArrayList()

    constructor(email: String, password: String) {
        this.email = email
        this.encryptedPassword = this.encryptPassword(password)
    }

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.encryptedPassword = this.encryptPassword(password)
    }

    private fun encryptPassword(password: String): String {
        val encoder = BCryptPasswordEncoder(10, SecureRandom())
        return encoder.encode(password)
    }

    fun equalsPassword(rawPassword: String): Boolean {
        val encoder = BCryptPasswordEncoder(10, SecureRandom())
        return encoder.matches(rawPassword, encryptedPassword)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return listOf("USER")
                .map { role -> SimpleGrantedAuthority("ROLE_$role") }
                .toMutableSet()
    }

    override fun getPassword(): String {
        return encryptedPassword ?: ""
    }

    override fun getUsername(): String {
        return email ?: ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        val latestRecentlyUserToken = userTokens.sortedBy { it.id }.last()
        return latestRecentlyUserToken.accessToken.isValid()
    }

    override fun isEnabled(): Boolean {
        return isCredentialsNonExpired
    }
}