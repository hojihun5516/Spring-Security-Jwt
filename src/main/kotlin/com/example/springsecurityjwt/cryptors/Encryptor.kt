package com.example.springsecurityjwt.cryptors

interface Encryptor {
    fun encrypt(text: String): String

    fun decrypt(encryptedText: String): String
}
