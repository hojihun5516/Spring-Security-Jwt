package com.example.springsecurityjwt.utils

import com.example.springsecurityjwt.domains.Role
import com.example.springsecurityjwt.exceptions.InvalidValueException
import com.example.springsecurityjwt.exceptions.Messages.INVALID_USERNAME_FORMAT

object AuthenticationUtils {
    const val AUTHENTICATION_PASSWORD_FROM_FILTER = "AUTHENTICATION_PASSWORD_FROM_FILTER"
    private const val USERNAME_ROLE_DELIMITER = "||"

    /**
     * [username]과 로그인하려는 롤([role])을 구분자([USERNAME_ROLE_DELIMITER])를 이용하여 합친다.
     */
    fun joinUsernameAndRole(username: String, role: Role) = buildString(255) {
        append(username).append(USERNAME_ROLE_DELIMITER).append(role)
    }

    /**
     * [username]과 [role]을 분리한다
     */
    fun splitUsernameAndRole(username: String?): Pair<String, String> {
        return if (username.isNullOrEmpty() || !username.contains(USERNAME_ROLE_DELIMITER))
            throw InvalidValueException(INVALID_USERNAME_FORMAT, username)
        else username.substringBefore(USERNAME_ROLE_DELIMITER) to username.substringAfter(USERNAME_ROLE_DELIMITER)

    }
}
