package com.example.springsecurityjwt.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt.key-set")
class JwtKeySetsProperties {
    var keys: List<JwtKeySetProperties> = ArrayList()

    data class JwtKeySetProperties(
        var kid: String = "",
        var secretKey: String = ""
    )
}
