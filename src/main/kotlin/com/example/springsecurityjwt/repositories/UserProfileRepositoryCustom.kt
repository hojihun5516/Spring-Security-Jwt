package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.dtos.UserProfileDto

interface UserProfileRepositoryCustom {
    fun findAllWithUser(): List<UserProfileDto>
}
