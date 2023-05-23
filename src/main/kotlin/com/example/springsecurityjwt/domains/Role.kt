package com.example.springsecurityjwt.domains

enum class Role(title: String) {
    NONE("기본 값"),
    USER("일반 사용자"),
    ADMIN("관리자"),
    ;
}
