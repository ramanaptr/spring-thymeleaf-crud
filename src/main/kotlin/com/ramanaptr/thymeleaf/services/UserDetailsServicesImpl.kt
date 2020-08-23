package com.ramanaptr.thymeleaf.services

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServicesImpl : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

            override fun isEnabled(): Boolean = true

            override fun getUsername(): String {
                return "ramana"
            }

            override fun isCredentialsNonExpired(): Boolean = true

            override fun getPassword(): String {
                return "123456"
            }

            override fun isAccountNonExpired(): Boolean = true

            override fun isAccountNonLocked(): Boolean = true
        }
    }
}