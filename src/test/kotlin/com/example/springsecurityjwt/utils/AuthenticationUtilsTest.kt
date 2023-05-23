package com.example.springsecurityjwt.utils

import com.example.springsecurityjwt.domains.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthenticationUtilsTest {

    private val sut = AuthenticationUtils

    @Test
    fun `sut join username and role`() {
        // Arrange
        val username = "modernflow"
        val role = Role.USER
        val expected = "modernflow||USER"

        // Act
        val actual = sut.joinUsernameAndRole(username, role)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `sut split username and role`() {
        // Arrange
        val usernameWithRole = "modernflow||USER"
        val expectedUsername = "modernflow"
        val expectedRole = "USER"

        // Act
        val (username, role) = sut.splitUsernameAndRole(usernameWithRole)

        // Assert
        assertEquals(expectedUsername, username)
        assertEquals(expectedRole, role)
    }
}
