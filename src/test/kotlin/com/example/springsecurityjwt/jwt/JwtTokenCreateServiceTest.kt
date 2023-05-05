package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.domains.UserProfile
import com.example.springsecurityjwt.dtos.UserDto
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.support.Support
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class JwtTokenCreateServiceTest {
    @Test
    fun `sut should create jwt token when user profile dto is given`() {
        // Arrange
        val user = Support.fixture<User>()
        val userProfile = Support.fixture<UserProfile> {
            property(UserProfile::user) { user }
            property(UserProfile::userId) { user.id!! }
        }
        val userProfileDto = UserProfileDto(
            profileId = userProfile.id!!,
            profileName = userProfile.name,
            profileRole = userProfile.role,
            user = UserDto(
                userId = user.id!!,
                username = user.username,
                name = user.name,
                birthday = user.birthday,
            ),
        )

        // Act
        val actual = JwtTokenCreateService().createToken(userProfileDto)

        // Assert
        assertThat(actual)
            .isNotNull()
            .isNotBlank()
    }
}
