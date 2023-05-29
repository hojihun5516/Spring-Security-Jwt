package com.example.springsecurityjwt.converters

interface Encryptor {
    fun encrypt(text: String): String

    fun decrypt(encryptedText: String): String
}
