package com.funny.videos.api.services

import com.funny.videos.api.config.JwtToken
import com.funny.videos.api.exception.AuthException
import com.funny.videos.api.exception.NotLoginException
import com.funny.videos.api.models.entities.User
import com.funny.videos.api.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.naming.NoPermissionException
import javax.servlet.http.HttpServletRequest

@Service
class JwtServices(
    val request: HttpServletRequest
) {
    @Autowired
    private lateinit var jwtToken: JwtToken

    @Autowired
    private lateinit var repository: UserRepository

    /**
     * Find user by JWT token
     */
    fun find(): User {
        val token = getJwtToken()

        val authenticatedUser = jwtToken.extractUser(token)

        val user = repository.getById(authenticatedUser.id, true)
            ?: throw AuthException(msg = "${authenticatedUser.username} not exists" )

        // Check user still active and match with user in database
        val isValid = user.active && jwtToken.isValid(token, user)

        if (!isValid) {
            throw NoPermissionException("Authorization failed. Please sign in again!")
        }

        return authenticatedUser
    }

    /**
     * Get JWT from authorization header
     */
    fun getJwtToken(): String {
        val authorizationToken = request.getHeader("authorization")
        return if (authorizationToken.isNullOrBlank()) {
            // get JWT from cookie if the authorization header not exists token
            val cookie = request.cookies?.find { it.name == COOKIE_NAME } ?: throw NotLoginException("Cookie not exist")
            cookie.value ?: throw NotLoginException("Cookie values not found")
        } else {
            jwtToken.extractToken(authorizationToken)
        }
    }

    companion object {
        private const val COOKIE_NAME = "E-ACADEMY"
    }
}