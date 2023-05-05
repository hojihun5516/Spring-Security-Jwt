package com.example.springsecurityjwt.dtos

data class JwtTokenResponse(
    val accessToken: String,
    // TODO refresh token 기능 추가
    val refreshToken: String? = null,
)
