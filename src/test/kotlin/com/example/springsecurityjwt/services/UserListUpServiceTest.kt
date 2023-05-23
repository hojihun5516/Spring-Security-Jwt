package com.example.springsecurityjwt.services

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.dtos.UserDto
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.repositories.UserProfileRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class UserListUpServiceTest(
    @MockK private val userProfileRepository: UserProfileRepository
) {
    @InjectMockKs
    private lateinit var userListUpService: UserListUpService

    @Test
    fun `sut list up user profile dto list`() {
        // Arrange
        val userProfileDtoList = listOf(
            UserProfileDto(
                profileId = 1L,
                profileName = "modernflow",
                profileRole = Role.USER,
                user = UserDto(
                    userId = 1L,
                    username = "modernflow",
                    name = "modernflow",
                    birthday = null,
                ),
            ),
            UserProfileDto(
                profileId = 2L,
                profileName = "jihoon",
                profileRole = Role.USER,
                user = UserDto(
                    userId = 2L,
                    username = "jihoon",
                    name = "jihoon",
                    birthday = LocalDate.of(1994, 10, 31),
                ),
            ),
        )
        every { userProfileRepository.findAllWithUser() } returns userProfileDtoList

        // Act
        val actual = userListUpService.listUp()

        // Assertion
        assertEquals(userProfileDtoList, actual)
    }
}
