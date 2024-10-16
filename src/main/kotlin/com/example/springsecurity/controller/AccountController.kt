package com.example.springsecurity.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountController {

    @GetMapping("/my-account")
    fun getAccountDetails(): String {
        return "Welcome to your account"
    }
}
