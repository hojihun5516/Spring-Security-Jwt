package com.example.springsecurityjwt.support

import com.example.springsecurityjwt.domains.Role
import org.springframework.security.test.context.support.WithSecurityContext


@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory::class)
annotation class WithMockCustomUser(
    val username: String = "modernflow",
    val name: String = "moflow",
    val role: Role = Role.NONE
)
