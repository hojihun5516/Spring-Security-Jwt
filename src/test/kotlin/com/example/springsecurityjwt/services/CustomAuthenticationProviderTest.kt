package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.dtos.CustomUserDetails
import com.example.springsecurityjwt.support.Support
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockKExtension::class)
class CustomAuthenticationProviderTest(
    @MockK private val userDetailsService: CustomUserDetailsService,
    @MockK private val passwordEncoder: PasswordEncoder,
) {
    @InjectMockKs
    private lateinit var customAuthenticationProvider: CustomAuthenticationProvider

    @Test
    fun `sut should return Authentication when UsernamePasswordAuthenticationToken is given`() {
        val username = "modernflow||ROLE_USER"
        val password = "password"

        val userDetails = Support.fixture<CustomUserDetails>()

        every { userDetailsService.loadUserByUsername(username) } returns userDetails
        every { passwordEncoder.matches(password, userDetails.password) } returns true

        val authentication = UsernamePasswordAuthenticationToken(username, password)

        val result = customAuthenticationProvider.authenticate(authentication)

        assertEquals(userDetails, result.principal)
        assertEquals(password, result.credentials)
    }

    @Test
    fun `sut throw when password is not correctly`() {
        val username = "modernflow||ROLE_USER"
        val password = "password"

        val userDetails = Support.fixture<CustomUserDetails>()

        every { userDetailsService.loadUserByUsername(username) } returns userDetails
        every { passwordEncoder.matches(password, userDetails.password) } returns false

        val authentication = UsernamePasswordAuthenticationToken(username, password)

        val exception = assertThrows(BadCredentialsException::class.java) {
            customAuthenticationProvider.authenticate(authentication)
        }

        assertEquals("Invalid password", exception.message)
    }
}
