package com.example.projekat3.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projekat3.data.models.user.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}