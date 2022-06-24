package com.example.projekat3.data.repositories

import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.Stock
import io.reactivex.Observable
import io.reactivex.Single

interface StocksRepository {
    fun fetchAll(json: String): Observable<List<Stock>>
    fun searchStock (json: String): DetailedStock

}