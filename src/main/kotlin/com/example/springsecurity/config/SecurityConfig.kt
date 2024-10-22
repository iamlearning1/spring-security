package com.example.springsecurity.config

import com.example.springsecurity.config.filters.JWTTokenGeneratorFilter
import com.example.springsecurity.config.filters.JWTTokenValidatorFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.addFilterAfter(JWTTokenGeneratorFilter(), BasicAuthenticationFilter::class.java)

        http.addFilterBefore(JWTTokenValidatorFilter(), BasicAuthenticationFilter::class.java)

        http.authorizeHttpRequests {
            requests -> requests
                .requestMatchers("/accounts/**")
                .hasAnyRole("ADMIN")
                .requestMatchers("/notices", "/error", "/users/**")
                .permitAll()
        }

        http.formLogin { it.disable() }
        http.csrf { it.disable() }

        http.httpBasic { it.authenticationEntryPoint(CustomBasicAuthenticationEntryPoint()) }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}
