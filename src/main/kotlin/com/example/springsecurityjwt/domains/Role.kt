package com.example.springsecurityjwt.domains

enum class Role(title: String) {
    NONE("기본 값"),
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자"),
    ;
}
