package com.example.springsecurityjwt.dtos

import java.time.LocalDate

data class UserDto(
    val userId: UserId,
    val username: String,
    val name: String,
    val birthday: LocalDate?,
)
