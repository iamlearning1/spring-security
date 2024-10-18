package com.example.springsecurity.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomBasicAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.setHeader("error-reason", "Auth error")

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json;charset=UTF-8"

        response.writer.write("{ message: \"Something went wrong\" }")
    }
}
