package com.example.springsecurityjwt.dtos

import com.example.springsecurityjwt.domains.Role

data class UserProfileDto(
    val profileId: Long,
    val profileName: String,
    val profileRole: Role,
    val user: UserDto
)
