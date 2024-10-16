package com.example.springsecurity.repository

import com.example.springsecurity.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByEmail(email: String): Customer?
}
