package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SignInService(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
) {

    // TODO: custom exception
    fun signIn(signInRequest: SignInRequest): UserProfileDto {

        val user = userRepository.findByUsername(signInRequest.username)
            ?: throw Exception("username not found")

        val userProfile = userProfileRepository.findByUserIdAndRole(userId = user.id!!, role = signInRequest.role)
            ?: throw Exception("user profile not found")

        return UserProfileDto.from(userProfile)
    }
}
