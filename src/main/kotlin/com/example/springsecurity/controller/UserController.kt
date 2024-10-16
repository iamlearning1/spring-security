package com.example.springsecurity.controller

import com.example.springsecurity.entity.Customer
import com.example.springsecurity.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerUser(
        @RequestBody customer: Customer,
    ): ResponseEntity<String> {
        val password = passwordEncoder.encode(customer.password)

        customerRepository.save(customer.copy(password = password))

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
    }
}
