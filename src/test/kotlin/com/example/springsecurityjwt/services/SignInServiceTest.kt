import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.dtos.CustomUserDetails
import com.example.springsecurityjwt.dtos.SignInRequest
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.example.springsecurityjwt.services.CustomUserDetailsService
import com.example.springsecurityjwt.services.SignInService
import com.example.springsecurityjwt.support.Support
import com.example.springsecurityjwt.utils.AuthenticationUtils
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@ExtendWith(MockKExtension::class)
class SignInServiceTest(
    @MockK private val authenticationManager: AuthenticationManager,
    @MockK private val userDetailsService: CustomUserDetailsService,
) {
    @InjectMockKs
    private lateinit var sut: SignInService

    @Test
    fun `sut should signIn when valid request is given`() {
        // Arrange
        val username = "modernflow"
        val password = "password"
        val role = Role.ROLE_USER

        val signInRequest = SignInRequest(username, password, role)

        val customUsername = AuthenticationUtils.joinUsernameAndRole(username, role)
        val userDetails = Support.fixture<CustomUserDetails> {
            property(CustomUserDetails::id) { 1 }
            property(CustomUserDetails::pid) { 1 }
        }
        every { authenticationManager.authenticate(any()) } returns UsernamePasswordAuthenticationToken(
            customUsername,
            password,
        )
        every { userDetailsService.loadUserByUsername(customUsername) } returns userDetails

        // Act
        val actual: UserProfileDto = sut.signIn(signInRequest)

        // Assert
        assertEquals(userDetails.id, actual.user.userId)
        assertEquals(userDetails.pid, actual.profileId)
    }
}
