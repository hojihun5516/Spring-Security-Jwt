package com.example.springsecurityjwt.exceptions

object Messages {
    val USERNAME_ALREADY_EXISTS = "usernameAlreadyExists" to "Username already exists by username : {0}"
    val USER_NOT_FOUND_BY_USERNAME = "userNotFound" to "User not found by username : {0}"
    val USER_PROFILE_NOT_FOUND_BY_ID_AND_ROLE = "userProfileNotFound" to "User profile not found by user id : {0}, role: {1}"
    val JWT_KEY_NOT_FOUND_BY_KID = "jwtKeyNotFound" to "Jwt key not found by kid : {0}"
    val INVALID_USERNAME_FORMAT = "invalidUsernameFormat" to "invalidUsernameFormat by formatted username : {0}"
}
