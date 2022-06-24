package com.example.projekat3.data.repositories

import com.example.projekat3.data.models.stocks.Stock
import io.reactivex.Observable

interface StocksRepository {
    fun fetchAll(json: String): Observable<List<Stock>>
}