package com.funny.videos.api.models.responses

import com.funny.videos.api.models.entities.User

data class LoginResponse(
    val token: String,
    val user: User
)