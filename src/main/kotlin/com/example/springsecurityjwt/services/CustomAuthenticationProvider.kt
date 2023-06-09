package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.utils.AuthenticationUtils.AUTHENTICATION_PASSWORD_FROM_FILTER
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder

class CustomAuthenticationProvider(
    private val userDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.principal as String
        val password = authentication.credentials as String

        val userDetails = userDetailsService.loadUserByUsername(username)

        if (isAuthenticationFromFilter(password)) {
            return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
        }

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw BadCredentialsException("Invalid password")
        }

        return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
    }

    private fun isAuthenticationFromFilter(password: String): Boolean {
        return password == AUTHENTICATION_PASSWORD_FROM_FILTER
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}
