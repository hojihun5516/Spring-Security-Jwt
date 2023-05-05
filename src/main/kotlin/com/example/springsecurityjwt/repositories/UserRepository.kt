package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.dtos.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, UserId> {
    fun findByUsername(userName: String): User?
}
