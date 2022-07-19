package com.funny.videos.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
data class AuthException(
    private val msg: String?
) : BusinessException(
    reason = "Sign failed. Username or Password was incorrect.",
    message = msg?: ""
)

@ResponseStatus(HttpStatus.FORBIDDEN)
data class NotLoginException(
    private val msg: String?
) : BusinessException(
    reason = "Authentication failed. Please login again!",
    message = msg?: ""
)