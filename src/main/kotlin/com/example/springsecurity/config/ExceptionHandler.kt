package com.example.springsecurity.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    data class BadRequestException(
        val status: HttpStatus,
        val message: String?
    )

    @ExceptionHandler
    fun exceptionHandler(e: Exception): ResponseEntity<BadRequestException> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BadRequestException(status = HttpStatus.BAD_REQUEST, message = e.message)
        )
    }
}
