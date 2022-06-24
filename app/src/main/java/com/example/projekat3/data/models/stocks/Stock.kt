package com.example.projekat3.data.models.stocks

data class Stock(
    val id: Long,
    val name: String,
    val symbol: String,
    val currency: String,
    val last: Double,
    val changeFromPreviousClose: Double,
    val percentChangeFromPreviousClose: Double,
    val marketName: String,
    val recommendation: Recommendation,
    val chart: Chart
)
