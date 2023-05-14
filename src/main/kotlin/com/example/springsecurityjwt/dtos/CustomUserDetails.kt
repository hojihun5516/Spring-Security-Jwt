package com.example.springsecurityjwt.dtos

import com.example.springsecurityjwt.domains.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

class CustomUserDetails : UserDetails {
    var id = 0L
    var login = ""
    var pid = 0L
    var name = ""
    var role = Role.NONE
    var birthday: LocalDate? = null
    var encodedPassword = ""

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role.toString()))

    override fun getPassword() = encodedPassword

    override fun getUsername() = login

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}
