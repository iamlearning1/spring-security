package com.example.springsecurity.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notices")
class NoticeController {

    @GetMapping
    fun getNotices(): String {
        return "notices"
    }
}
