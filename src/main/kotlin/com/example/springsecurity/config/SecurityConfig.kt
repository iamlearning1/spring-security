package com.example.springsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // enables https protocol for all requests
        // http.requiresChannel { channelHandler -> channelHandler.anyRequest().requiresSecure() }

        val csrfTokenRequestAttributeHandler = CsrfTokenRequestAttributeHandler()

        http.authorizeHttpRequests {
            requests -> requests
                .requestMatchers("/accounts/**")
                .authenticated()
                .requestMatchers("/notices", "/error", "/users/**", "/invalid-session")
                .permitAll()
        }

        http.formLogin { it.disable() }
        http.csrf {
            it
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        }
        http.httpBasic { it.authenticationEntryPoint(CustomBasicAuthenticationEntryPoint()) }

        http.addFilterAfter(CsrfCookieFilter(), AnonymousAuthenticationFilter::class.java)

        http.sessionManagement { it.invalidSessionUrl("/invalid-session") }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}
