package com.example.projekat3.data.repositories


import com.example.projekat3.data.datasource.remote.StockService
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.data.models.stocks.StockResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.util.concurrent.atomic.AtomicLong

class StocksRepositoryImpl(private val remoteDataSource: StockService) : StocksRepository {

    private var idCounter: AtomicLong = AtomicLong(0)

    override fun fetchAll(json: String): Observable<List<Stock>> {//Observable<Resource<Unit>>
        val gson = Gson()
        val stocksType = object : TypeToken<List<StockResponse>>() {}.type
        val stocks: List<StockResponse> = gson.fromJson(json, stocksType)
        val stocksObservable = Observable.fromArray(stocks)

        return stocksObservable.map {
            it.map { stockResponse: StockResponse ->
                Stock(
                    id = idCounter.getAndIncrement(),
                    name = stockResponse.name,
                    symbol = stockResponse.symbol,
                    currency = stockResponse.currency,
                    last = stockResponse.last,
                    changeFromPreviousClose = stockResponse.changeFromPreviousClose,
                    percentChangeFromPreviousClose = stockResponse.percentChangeFromPreviousClose,
                    marketName = stockResponse.marketName,
                    recommendation = stockResponse.recommendation,
                    chart = stockResponse.chart,
                )
            }
        }
    }

}