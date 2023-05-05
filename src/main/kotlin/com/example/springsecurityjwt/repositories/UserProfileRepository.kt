package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.domains.UserProfile
import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileRepository:JpaRepository<UserProfile, Long>
