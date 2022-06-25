package com.example.projekat3.data.models.stocks

data class LocalStock (
    val id: Long,
    val userId: Long,
    val name: String,
    val numberOf: Int,
    val symbol: String,
    val boughtDate: String,
    val value: Double
)