package com.example.springsecurityjwt.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.SigningKeyResolverAdapter
import org.springframework.stereotype.Component
import java.security.Key

@Component
class SigningKeyResolver(
    private val jwtKey: JwtKey,
) : SigningKeyResolverAdapter() {
    override fun resolveSigningKey(jwsHeader: JwsHeader<*>, claims: Claims): Key {
        val kid = jwsHeader.keyId
        return jwtKey.getKey(kid)
    }
}
