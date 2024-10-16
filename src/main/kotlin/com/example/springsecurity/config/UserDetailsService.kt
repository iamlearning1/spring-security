package com.example.springsecurity.config

import com.example.springsecurity.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val customer = customerRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User details not found for the user: $username")

        val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(customer.role))

        return User(
            customer.email,
            customer.password,
            authorities
        )
    }
}
