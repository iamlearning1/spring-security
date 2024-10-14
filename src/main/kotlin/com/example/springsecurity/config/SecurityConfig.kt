package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.password.CompromisedPasswordChecker
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker

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
        val user = User.withUsername("user").password("{bcrypt}\$2y\$10\$9zPaiFzoL5yVLEeGmM37N.SlBbjnpCH7vQ8K4magXWXyYJ8Qy7qX6").authorities("read").build()
        val admin = User.withUsername("admin").password("{bcrypt}\$2y\$10$/zH2EkJqdDZiJYkMs8ioasOWTz/bk8TP24v2ka/wTc0fRc5UEbvFgS").authorities("admin").build()

        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun compromisedPasswordChecker(): CompromisedPasswordChecker {
        return HaveIBeenPwnedRestApiPasswordChecker()
    }
}
