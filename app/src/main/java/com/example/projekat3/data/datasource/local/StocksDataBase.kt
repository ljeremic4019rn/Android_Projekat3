package com.example.projekat3.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projekat3.data.models.stocks.LocalStockEntity

@Database(
    entities = [LocalStockEntity::class],
    version = 1,
    exportSchema = false)

@TypeConverters()
abstract class StocksDataBase : RoomDatabase(){
    abstract fun getStocksDao(): StockDao
}