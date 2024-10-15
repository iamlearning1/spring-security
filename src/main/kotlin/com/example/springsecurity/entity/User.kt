package com.example.springsecurity.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    val username: String,
    val password: String,
    val enabled: Boolean,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authorities: List<Authority> = emptyList(),
)
