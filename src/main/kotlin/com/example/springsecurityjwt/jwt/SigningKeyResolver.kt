package com.example.springsecurityjwt.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.SigningKeyResolverAdapter
import java.security.Key


class SigningKeyResolver : SigningKeyResolverAdapter() {
    override fun resolveSigningKey(jwsHeader: JwsHeader<*>, claims: Claims): Key {
        val kid = jwsHeader.keyId
        return JwtKey.getKey(kid)
    }

    companion object {
        val signingKeyResolver = SigningKeyResolver()
    }
}
