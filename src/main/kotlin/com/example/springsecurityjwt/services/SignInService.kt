package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.utils.AuthenticationUtils.joinUsernameAndRole
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SignInService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
) {

    fun signIn(signInRequest: SignInRequest): UserProfileDto {
        val customUsername = joinUsernameAndRole(signInRequest.username, signInRequest.role)

        val passwordResult = UsernamePasswordAuthenticationToken(
            customUsername,
            signInRequest.password,
        ).let { authenticationToken ->
            authenticationManager.authenticate(authenticationToken)
        }

        val userDetails = userDetailsService.loadUserByUsername(customUsername)

        return UserProfileDto.from(userDetails)
    }
}
