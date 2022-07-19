package com.funny.videos.api.controllers

import com.funny.videos.api.models.requests.LoginRequest
import com.funny.videos.api.models.responses.LoginResponse
import com.funny.videos.api.services.AuthServices
import com.funny.videos.api.services.JwtServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthRestController {

    @Autowired
    private lateinit var services: AuthServices

    @Autowired
    private lateinit var jwtServices: JwtServices

    @PostMapping("/login")
    fun login(@RequestBody model: LoginRequest): LoginResponse {
        val acceptedRequest = model.toValidRequest()
        acceptedRequest.validate()

        return services.login(login = acceptedRequest)
    }

    @PostMapping("/verify")
    fun verify() = LoginResponse(
        user = jwtServices.find(),
        token = jwtServices.getJwtToken()
    )
}