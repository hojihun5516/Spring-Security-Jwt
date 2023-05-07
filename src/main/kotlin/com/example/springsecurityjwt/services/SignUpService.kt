package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.dtos.SignUpRequest
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.mappers.toUser
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpService(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
) {
    fun signUp(signUpRequest: SignUpRequest): UserProfileDto {
        checkUserExists(username = signUpRequest.username)

        val user = userRepository.save(signUpRequest.toUser())

        val userProfile = userProfileRepository.save(user.toUserRoleProfile())

        return UserProfileDto.from(userProfile)
    }

    private fun checkUserExists(username: String) {
        // TODO: custom exception
        userRepository.findByUsername(username)?.let { throw Exception("already exists username") }
    }
}
