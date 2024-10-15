package com.example.springsecurity.entity

import jakarta.persistence.*

@Entity
@Table(name = "authorities")
data class Authority(
    @Id
    val username: String,
    val authority: String,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "authority_id")
    val user: User
)
