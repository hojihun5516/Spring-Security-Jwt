package com.example.springsecurityjwt.configs

import com.example.springsecurityjwt.cryptors.AesEncryptor
import com.example.springsecurityjwt.cryptors.Encryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EncryptorConfig {
    @Bean
    @ConditionalOnProperty(prefix = "crypto.aes256", name = ["key", "iv"])
    fun aes256Encryptor(
        @Value("\${crypto.aes256.key}")
        key: String,

        @Value("\${crypto.aes256.iv}")
        iv: String,
    ): Encryptor {
        return AesEncryptor.create(key, iv)
    }
}
