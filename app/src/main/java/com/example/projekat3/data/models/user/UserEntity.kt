package com.example.projekat3.data.models.user

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val balance: Double,
    val portfolioValue: Double,
)