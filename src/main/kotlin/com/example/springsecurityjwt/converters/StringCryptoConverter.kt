package com.example.springsecurityjwt.converters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringCryptoConverter(
    private val encryptor: Encryptor,
) : AttributeConverter<String, String?> {

    override fun convertToDatabaseColumn(attribute: String?): String? {
        return try {
            encryptor.encrypt(attribute.toString())
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    override fun convertToEntityAttribute(dbData: String?): String {
        return try {
            encryptor.decrypt(dbData!!)
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}

