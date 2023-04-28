package com.example.springsecurityjwt.domains

enum class Role(title: String) {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자"),
    ;
}
