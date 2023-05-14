package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.repositories.UserProfileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserListUpService(
    private val userProfileRepository: UserProfileRepository,
) {
    fun listUp(): List<UserProfileDto> {
        return userProfileRepository.findAllWithUser()
    }
}
