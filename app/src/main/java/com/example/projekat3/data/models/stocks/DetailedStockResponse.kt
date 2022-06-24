package com.example.projekat3.data.models.stocks

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DetailedStockResponse (
    val symbol: String,
    val name: String,
    val currency: String,
    val open: Double,
    val close: Double,
    val bid: Double,
    val ask: Double,
    val metrics: Metrics,
    val chart: Chart
)