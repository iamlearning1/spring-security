package com.example.springsecurity.config

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication?.name
        val password = authentication?.credentials.toString()

        if (username == null) throw UsernameNotFoundException("Username not provided");

        val userDetails = userDetailsService.loadUserByUsername(username)

        val matches = passwordEncoder.matches(password, userDetails.password)

        if (matches) {
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }

        throw BadCredentialsException("Something went wrong");
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}
