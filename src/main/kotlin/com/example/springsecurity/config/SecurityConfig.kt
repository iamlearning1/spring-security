package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            requests -> requests
                .requestMatchers("/accounts/**").authenticated()
                .requestMatchers("/notices", "/error", "/users/**").permitAll()
        }

        http.formLogin { it.disable() }
        http.csrf { it.disable() }
        http.httpBasic { }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

//    @Bean
//    fun userDetailsService(dataSource: DataSource): UserDetailsService {
//        return JdbcUserDetailsManager(dataSource)
//    }

//    @Bean
//    fun compromisedPasswordChecker(): CompromisedPasswordChecker {
//        return HaveIBeenPwnedRestApiPasswordChecker()
//    }
}
