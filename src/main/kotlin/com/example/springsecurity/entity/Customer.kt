package com.example.springsecurity.entity

import jakarta.persistence.*

@Entity
@Table(name = "customers")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val email: String,
    val password: String,
    val role: String,
)
