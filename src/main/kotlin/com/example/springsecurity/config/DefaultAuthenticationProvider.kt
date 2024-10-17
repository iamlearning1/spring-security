package com.example.springsecurity.config

import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
@Profile("default")
class DefaultAuthenticationProvider (
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication?.name ?: throw UsernameNotFoundException("Username not provided")
        val userDetails = userDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}
