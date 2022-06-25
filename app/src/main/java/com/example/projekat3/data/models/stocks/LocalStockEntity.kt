package com.example.projekat3.data.models.stocks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
class LocalStockEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userId: Long,
    val name: String,
    val numberOf: Int,
    val symbol: String,
    val boughtDate: String,
    val value: Double
)