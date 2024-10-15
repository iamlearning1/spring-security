package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.password.CompromisedPasswordChecker
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker
import javax.sql.DataSource

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
    fun userDetailsService(dataSource: DataSource): UserDetailsService {
        return JdbcUserDetailsManager(dataSource)
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
