package com.example.springsecurityjwt.support

import com.example.springsecurityjwt.dtos.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockCustomUserSecurityContextFactory : WithSecurityContextFactory<WithMockCustomUser> {
    override fun createSecurityContext(customUser: WithMockCustomUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val principal = CustomUserDetails().apply {
            customUser.name
            customUser.username
            role = customUser.role
        }
        val auth = UsernamePasswordAuthenticationToken(principal, "password", principal.authorities)
        context.authentication = auth
        return context
    }
}
