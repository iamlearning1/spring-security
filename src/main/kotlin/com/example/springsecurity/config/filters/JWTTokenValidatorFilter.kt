package com.example.springsecurity.config.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTTokenValidatorFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header != null) {
            val token = header.replace("Bearer ", "")

            try {
                val secret = environment.getProperty(
                    "JWT_SECRET_KEY", "klsdf92o9jflsdkfi3e#2eoiufdjjk3wei"
                )

                val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

                if (secretKey != null) {
                    val claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .payload

                    val username = claims["username"] as String

                    // simplify how to get the role
                    val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(claims["authorities"].toString()))

                    val authentication = UsernamePasswordAuthenticationToken(username, null, authorities)

                    SecurityContextHolder.getContext().authentication = authentication
                }


            } catch (ex: Exception) {
                throw BadCredentialsException("Invalid token")
            }
        }

        filterChain.doFilter(request, response)
    }

    @Throws(ServletException::class)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return (request.servletPath == "/users/login")
    }
}
