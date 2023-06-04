package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.dtos.CustomUserDetails
import com.example.springsecurityjwt.exceptions.Messages.USER_NOT_FOUND_BY_USERNAME
import com.example.springsecurityjwt.exceptions.Messages.USER_PROFILE_NOT_FOUND_BY_ID_AND_ROLE
import com.example.springsecurityjwt.exceptions.NotFoundException
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
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): CustomUserDetails {
        val (splitUsername, role) = splitUsernameAndRole(username)
        val user = userRepository.findByUsername(splitUsername)
            ?: throw NotFoundException(USER_NOT_FOUND_BY_USERNAME, splitUsername)

        val userProfile = userProfileRepository.findByUserIdAndRole(userId = user.id!!, role = Role.valueOf(role))
            ?: throw NotFoundException(USER_PROFILE_NOT_FOUND_BY_ID_AND_ROLE, user.id, role)

        return CustomUserDetails().apply {
            this.id = user.id!!
            this.login = splitUsername
            this.pid = userProfile.id!!
            this.name = userProfile.name
            this.role = userProfile.role
            this.encodedPassword = user.password
        }
    }
}
