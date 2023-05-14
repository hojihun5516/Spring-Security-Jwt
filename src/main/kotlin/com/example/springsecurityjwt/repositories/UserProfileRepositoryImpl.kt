package com.example.springsecurityjwt.repositories

import com.example.springsecurityjwt.domains.QUser.user
import com.example.springsecurityjwt.domains.QUserProfile.userProfile
import com.example.springsecurityjwt.dtos.UserDto
import com.example.springsecurityjwt.dtos.UserProfileDto
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager

class UserProfileRepositoryImpl(em: EntityManager) : UserProfileRepositoryCustom {
    private val queryFactory = JPAQueryFactory(em)

    override fun findAllWithUser(): List<UserProfileDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    UserProfileDto::class.java,
                    userProfile.id,
                    userProfile.name,
                    userProfile.role,
                    Projections.constructor(
                        UserDto::class.java,
                        user.id,
                        user.username,
                        user.name,
                        user.birthday,
                    ),
                ),
            )
            .from(userProfile)
            .innerJoin(userProfile).on(user.id.eq(userProfile.id))
            .fetch()
    }
}
