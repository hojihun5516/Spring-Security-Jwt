import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.domains.User
import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.UserDto
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.repositories.UserProfileRepository
import com.example.springsecurityjwt.repositories.UserRepository
import com.example.springsecurityjwt.services.SignInService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SignInServiceTest(
    @MockK private val userProfileRepository: UserProfileRepository,
    @MockK private val userRepository: UserRepository,
) {
    @InjectMockKs
    private lateinit var sut: SignInService

    @Test
    fun `sut should signIn when valid request is given`() {
        // Arrange
        val signInRequest = SignInRequest(
            username = "testuser",
            password = "testpassword",
            role = Role.ROLE_USER,
        )

        val user = User(username = "testuser", password = "testpassword", name = "test", birthday = null)
            .apply { id = 1L }
        val userProfile = user.toUserRoleProfile().apply { id = 1L }
        every { userRepository.findByUsername(signInRequest.username) } returns user
        every { userProfileRepository.findByUserIdAndRole(user.id!!, signInRequest.role) } returns userProfile

        // Act
        val result: UserProfileDto = sut.signIn(signInRequest)

        // Assert
        assertThat(result)
            .hasFieldOrPropertyWithValue("profileId", userProfile.id)
            .hasFieldOrPropertyWithValue("profileName", userProfile.name)
            .hasFieldOrPropertyWithValue("profileRole", userProfile.role)
            .hasFieldOrPropertyWithValue("user", UserDto.from(user))
    }

    @Test
    fun `sut should exception when invalid request is given`() {
        // Arrange
        val signInRequest = SignInRequest(
            username = "testuser",
            password = "testpassword",
            role = Role.ROLE_USER,
        )

        every { userRepository.findByUsername(signInRequest.username) } returns null

        // Act & Assert
        assertThrows<Exception> { sut.signIn(signInRequest) }
    }

    //
    @Test
    fun `sut should exception when user profile is invalid`() {
        // Arrange
        val signInRequest = SignInRequest(
            username = "testuser",
            password = "testpassword",
            role = Role.ROLE_USER,
        )

        val user = User(username = "testuser", password = "testpassword", name = "test", birthday = null)
            .apply { id = 1L }

        every { userRepository.findByUsername(signInRequest.username) } returns user
        every { userProfileRepository.findByUserIdAndRole(user.id!!, signInRequest.role) } returns null

        // Act & Assert
        assertThrows<Exception> { sut.signIn(signInRequest) }
    }
}
