package com.example.springsecurity.config.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Instant
import java.util.*

class JWTTokenGeneratorFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication != null) {
            val secret = environment.getProperty(
                "JWT_SECRET_KEY", "klsdf92o9jflsdkfi3e#2eoiufdjjk3wei"
            )

            val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

            val token = Jwts.builder()
                .issuer("Banking App")
                .subject("JWT Token")
                .claim("username", authentication.name)
                .claim("authorities", authentication.authorities.map { it.authority }.first())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(60000)))
                .signWith(secretKey)
                .compact()

            response.addHeader("Authorization", "Bearer $token")
        }

        filterChain.doFilter(request, response)
    }

    @Throws(ServletException::class)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return (request.servletPath != "/users/login")
    }
}
