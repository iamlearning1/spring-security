package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            requests -> requests
                .requestMatchers("/accounts/**").authenticated()
                .requestMatchers("/notices", "/error").permitAll()
        }

        http.formLogin { it.disable() }
        http.httpBasic { }

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user").password("{noop}user12345").authorities("read").build()
        val admin = User.withUsername("admin").password("{noop}admin12345").authorities("admin").build()

        return InMemoryUserDetailsManager(user, admin)
    }
}
