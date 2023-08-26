package com.example.springsecurityjwt.converters

import com.example.springsecurityjwt.cryptors.Encryptor
import java.time.LocalDate
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LocalDateCryptoConverter(
    private val encryptor: Encryptor,
) : AttributeConverter<LocalDate?, String?> {

    override fun convertToDatabaseColumn(attribute: LocalDate?): String? {
        return try {
            if(attribute == null) { null }
            else encryptor.encrypt(attribute.toString())
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    override fun convertToEntityAttribute(dbData: String?): LocalDate? {
        return try {
            if(dbData == null) { null }
            else LocalDate.parse(encryptor.decrypt(dbData))
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}

