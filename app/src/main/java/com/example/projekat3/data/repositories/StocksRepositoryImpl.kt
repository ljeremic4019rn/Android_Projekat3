package com.example.projekat3.data.repositories


import com.example.projekat3.data.datasource.remote.StockService
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.DetailedStockResponse
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

    override fun searchStock(json: String): DetailedStock {
        val gson = Gson()
        val dsType = object : TypeToken<DetailedStockResponse>() {}.type
        val dsResponse: DetailedStockResponse = gson.fromJson(json, dsType)

        return DetailedStock(
                symbol = dsResponse.symbol,
                name = dsResponse.name,
                currency = dsResponse.currency,
                last = dsResponse.last,
                open = dsResponse.open,
                close = dsResponse.close,
                bid = dsResponse.bid,
                ask = dsResponse.ask,
                metrics = dsResponse.metrics,
                chart = dsResponse.chart
            )
    }


//    override fun searchStock(json: String): Single<DetailedStock> {
//        val gson = Gson()
//        val dsType = object : TypeToken<DetailedStockResponse>() {}.type
//        val dsResponse: DetailedStockResponse = gson.fromJson(json, dsType)
//        val dsObservable = Observable.fromArray(ds)
//
//        val detailedStock = DetailedStock(
//            symbol = dsResponse.symbol,
//            name = dsResponse.name,
//            currency = dsResponse.currency,
//            open = dsResponse.open,
//            close = dsResponse.close,
//            bid = dsResponse.bid,
//            ask = dsResponse.ask,
//            metrics = dsResponse.metrics,
//            chart = dsResponse.chart
//        )
//
//        return Observable.fromCallable(detailedStock)
//
//    }
}
