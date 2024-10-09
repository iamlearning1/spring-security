package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            requests -> requests
                .requestMatchers("/accounts/**").authenticated()
                .requestMatchers("/notices").permitAll()
        }

        http.formLogin { }
        http.httpBasic { }

        return http.build()
    }
}
