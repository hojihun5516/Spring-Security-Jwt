package com.example.springsecurityjwt.dtos

import java.time.LocalDate

data class SignUpRequest(
    val username: String,
    val password: String,
    val name: String,
    val birthday: LocalDate?,
)
