package com.funny.videos.api.config

import com.funny.videos.api.exception.NotLoginException
import com.funny.videos.api.models.entities.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.Serializable
import java.nio.charset.Charset
import java.util.*

@Component
class JwtToken : Serializable {

    fun generateToken(user: User): String =
        mapOf<String, Any>("id" to user.id, "username" to user.username)
            .let {
                Jwts.builder()
                    .setId(user.username)
                    .setSubject(user.username)
                    .setClaims(it)
                    .setIssuedAt(Date(System.currentTimeMillis()))
                    .setExpiration(
                        Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)
                    )
                    .signWith(JWT_KEY)
                    .compact()
            }

    fun extractUser(jwt: String) =
        extractClaims(jwt).let {
            User(
                id = (it["id"] as Int).toLong(),
                username = it["username"] as String,
                active = true,
                password = ""
            )
        }

    fun extractClaims(jwt: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(JWT_KEY)
                .build()
                .parseClaimsJws(jwt)
                .body
        } catch (ex: Exception) {
            System.err.println(ex)
            throw NotLoginException(ex.message)
        }

    /**
     * Extract token from [authorization] string in HTTPRequest
     * @return the JWT value
     */
    fun extractToken(authorization: String?) = authorization?.substring(7) ?: ""

    /**
     * Check is [jwtToken] expired
     *
     * @return
     * - FALSE - if expired in JWT is BEFORE current date - TRUE - if expired in JWT is AFTER
     * current date
     */
    fun isExpired(jwtToken: String?) = extractClaims(jwtToken ?: "").expiration.after(Date())

    /**
     * Compare user's information from [jwtToken] with [user]
     *
     * @return
     * - FALSE - if not equals and token was expired - TRUE - if equal and token wasn't expired
     */
    fun isValid(jwtToken: String?, user: User?): Boolean {
        val userFromToken = extractUser(jwtToken ?: "")
        return isExpired(jwtToken) &&
                userFromToken.id == user?.id &&
                userFromToken.username.equals(user.username, ignoreCase = true)
    }

    companion object {

        private const val serialVersionUID = -2550185165626007488L
        const val JWT_TOKEN_VALIDITY = 3600 * 24 * 30 * 1000L

        @JvmStatic
        private val JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    }
}