package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.dtos.CustomUserDetails
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import com.example.springsecurityjwt.utils.AuthenticationUtils.splitUsernameAndRole
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userProfileRepository: UserProfileRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {

    // TODO: custom exception
    override fun loadUserByUsername(username: String?): CustomUserDetails {
        val (splitUsername, role) = splitUsernameAndRole(username)
        val user = userRepository.findByUsername(splitUsername)
            ?: throw Exception("username not found")

        val userProfile = userProfileRepository.findByUserIdAndRole(userId = user.id!!, role = Role.valueOf(role))
            ?: throw Exception("user profile not found")

        return CustomUserDetails().apply {
            this.id = user.id!!
            this.login = splitUsername
            this.pid = userProfile.id!!
            this.name = userProfile.name
            this.role = userProfile.role
            this.encodedPassword = passwordEncoder.encode(user.password)
        }
    }
}
