package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.domains.UserProfile
import com.example.springsecurityjwt.dtos.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    fun findByUserIdAndRole(userId: UserId, role: Role): UserProfile?
}
