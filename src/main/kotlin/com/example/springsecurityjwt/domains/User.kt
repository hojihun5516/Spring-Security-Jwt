package com.example.springsecurityjwt.domains

import com.example.springsecurityjwt.converters.LocalDateCryptoConverter
import com.example.springsecurityjwt.dtos.UserId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    var username: String,
    var password: String,
    var name: String,
    @Convert(converter = LocalDateCryptoConverter::class)
    var birthday: LocalDate?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UserId? = null

    var deletedAt: LocalDateTime? = null
        protected set

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    fun withdraw() {
        deletedAt = LocalDateTime.now()
    }

    fun toUserRoleProfile(): UserProfile {
        return UserProfile(
            name = name,
        ).also {
            it.role = Role.USER
            it.user = this
            it.userId = this.id!!
        }
    }
}
