package com.example.projekat3.data.models.user

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val balance: Double,
    val portfolioValue: Double,
)