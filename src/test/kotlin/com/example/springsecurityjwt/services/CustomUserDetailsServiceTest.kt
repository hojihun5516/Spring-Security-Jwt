package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.domains.UserProfile
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import com.example.springsecurityjwt.utils.AuthenticationUtils
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockKExtension::class)
class CustomUserDetailsServiceTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val userProfileRepository: UserProfileRepository,
    @MockK private val passwordEncoder: PasswordEncoder,
) {
    @InjectMockKs
    private lateinit var sut: CustomUserDetailsService

    @Test
    fun `sut return custom user details`() {
        // Arrange
        val username = "modernflow"
        val role = Role.USER

        val userId = 1L
        val userProfileId = 1L
        val name = "moflow"
        val encodedPassword = "encoded-password"

        val user = User(
            username = username,
            password = "password",
            name = name,
            birthday = null,
        ).apply { this.id = userId }

        val userProfile = UserProfile(name).apply {
            this.id = userProfileId
            this.userId = userId
            this.role = role
        }

        every { userRepository.findByUsername(username) } returns user
        every { userProfileRepository.findByUserIdAndRole(userId, role) } returns userProfile
        every { passwordEncoder.encode(user.password) } returns encodedPassword

        // Act
        val actual =
            sut.loadUserByUsername(AuthenticationUtils.joinUsernameAndRole(username, role))

        // Assert
        assertThat(actual)
            .hasFieldOrPropertyWithValue("id", userId)
            .hasFieldOrPropertyWithValue("login", username)
            .hasFieldOrPropertyWithValue("pid", userProfileId)
            .hasFieldOrPropertyWithValue("name", name)
            .hasFieldOrPropertyWithValue("role", role)
    }

    @Test
    fun `sut loadUserByUsername should throw when user is not exists`() {
        // Arrange
        val username = "modernflow"
        val role = Role.USER

        every { userRepository.findByUsername(username) } returns null

        // Act & Assert
        val exception = assertThrows(Exception::class.java) {
            sut.loadUserByUsername(AuthenticationUtils.joinUsernameAndRole(username, role))
        }

        assertEquals("userNotFound", exception.message)
    }

    @Test
    fun `sut loadUserByUsername should throw when user profile is not exists`() {
        // Arrange
        val username = "modernflow"
        val name = "moflow"
        val role = Role.USER

        val userId = 1L

        val user = User(
            username = username,
            password = "password",
            name = name,
            birthday = null,
        ).apply { this.id = userId }

        every { userRepository.findByUsername(username) } returns user
        every { userProfileRepository.findByUserIdAndRole(userId, role) } returns null

        // Act & Assert
        val exception = assertThrows(Exception::class.java) {
            sut.loadUserByUsername(AuthenticationUtils.joinUsernameAndRole(username, role))
        }

        assertEquals("userProfileNotFound", exception.message)
    }
}
