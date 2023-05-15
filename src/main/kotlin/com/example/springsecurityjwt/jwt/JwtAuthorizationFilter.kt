package com.example.springsecurityjwt.jwt

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.services.CustomAuthenticationProvider
import com.example.springsecurityjwt.services.CustomUserDetailsService
import com.example.springsecurityjwt.utils.AuthenticationUtils.AUTHENTICATION_PASSWORD_FROM_FILTER
import com.example.springsecurityjwt.utils.AuthenticationUtils.joinUsernameAndRole
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 * JWT를 이용한 인증
 */
class JwtAuthorizationFilter(
    private val customUserDetailsService: CustomUserDetailsService,
    private val customAuthenticationProvider: CustomAuthenticationProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            if (SecurityContextHolder.getContext().authentication != null) {
                throw Exception("SecurityContextHolder 에러")
            }

            val token = authorizationHeader.substring(7)
            val username = extractUsernameFromToken(token)
            val userDetails = customUserDetailsService.loadUserByUsername(username)
            val authenticationToken = UsernamePasswordAuthenticationToken(
                username,
                AUTHENTICATION_PASSWORD_FROM_FILTER,
                userDetails.authorities
            )
            SecurityContextHolder.getContext().authentication =
                customAuthenticationProvider.authenticate(authenticationToken)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractUsernameFromToken(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKeyResolver(SigningKeyResolver.signingKeyResolver)
            .build()
            .parseClaimsJws(token)
            .body
        return joinUsernameAndRole(claims.subject, Role.valueOf(claims["role"].toString()))
    }
}
