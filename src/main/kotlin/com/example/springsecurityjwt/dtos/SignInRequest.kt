package com.example.springsecurityjwt.dtos

import com.example.springsecurityjwt.domains.Role

data class SignInRequest(
    val username: String,
    val password: String,
    val role: Role,
)
