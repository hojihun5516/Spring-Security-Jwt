package com.example.springsecurityjwt.converters

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesEncryptor(
    private val keyBytes: ByteArray,
    private val ivBytes: ByteArray,
) : Encryptor {

    override fun encrypt(text: String): String {
        val encrypted = encryptCipher().doFinal(text.toByteArray(Charsets.UTF_8))
        return String(Base64.getEncoder().encode(encrypted))
    }

    override fun decrypt(encryptedText: String): String {
        try {
            val decode = Base64.getDecoder().decode(encryptedText.toByteArray())
            return String(decryptCipher().doFinal(decode), Charsets.UTF_8)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun encryptCipher() = getCipher(Cipher.ENCRYPT_MODE)

    private fun decryptCipher() = getCipher(Cipher.DECRYPT_MODE)

    private fun getCipher(opmode: Int): Cipher {
        return Cipher.getInstance(TRANSFORMATION).also {
            it.init(opmode, SecretKeySpec(keyBytes, ALGORITHM_AES), IvParameterSpec(ivBytes))
        }
    }

    companion object {
        private const val ALGORITHM_AES = "AES"
        private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

        fun create(key: String, iv: String): AesEncryptor {
            return AesEncryptor(key.toByteArray(), iv.toByteArray())
        }
    }
}
