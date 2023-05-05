package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.domains.UserProfile
import com.example.springsecurityjwt.support.Support
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JwtTokenCreateServiceTest {
    @Test
    fun `sut should create jwt token when user profile is given`() {
        // Arrange
        val user = Support.fixture<User>()
        val userProfile = Support.fixture<UserProfile> {
            property(UserProfile::user) { user }
            property(UserProfile::userId) { user.id!! }
        }

        // Act
        val actual = JwtTokenCreateService().createToken(userProfile)

        // Assert
        assertThat(actual)
            .isNotNull()
            .isNotBlank()
    }
}
