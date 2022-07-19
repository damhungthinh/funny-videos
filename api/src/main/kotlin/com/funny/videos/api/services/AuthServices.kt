package com.funny.videos.api.services

import com.funny.videos.api.config.JwtToken
import com.funny.videos.api.exception.AuthException
import com.funny.videos.api.models.entities.User
import com.funny.videos.api.models.requests.LoginRequest
import com.funny.videos.api.models.responses.LoginResponse
import com.funny.videos.api.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServices {

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var jwtToken: JwtToken

    fun login(login: LoginRequest): LoginResponse {
        var user = repository.getByUsername(username = login.username!!)

        // Register new user if login in first time
        if (user == null) {
            val pwd = passwordEncoder.encode(login.password)
            user = User(
                username = login.username,
                password = pwd,
                active = true
            )
            repository.insert(user)
        } else {
            // Otherwise, check pwd for existed user
            val pwdMatched = passwordEncoder.matches(login.password, user.password)

            if (!pwdMatched) {
                throw AuthException("Password incorrect.")
            }
        }

        return LoginResponse(
            user = user,
            token = jwtToken.generateToken(user = user)
        )
    }
}