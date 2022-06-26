package com.example.projekat3.data.repositories

import com.example.projekat3.data.datasource.local.StockDao
import com.example.projekat3.data.datasource.local.UserDao
import com.example.projekat3.data.models.stocks.*
import com.example.projekat3.data.models.user.UserEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observable

class PortfolioRepositoryImpl(private val userDao: UserDao, private val stockDao: StockDao) :
    PortfolioRepository {


    override fun getUserByNameMailPass(username: String,email: String,password: String): Observable<UserEntity> {
        return userDao.getUserByUsernameEmailPass(username, email, password)
    }


    override fun insertUser(user: UserEntity): Completable {
        return userDao.insert(user)
    }


    override fun getAllStocksFromUser(userId: Long): Observable<List<LocalStock>> {
        return stockDao
            .getAllStocksFromUser(userId)
            .map { it ->
                it.map {
                    LocalStock(
                        it.id,
                        it.userId,
                        it.name,
                        it.numberOf,
                        it.symbol,
                        it.boughtDate,
                        it.value
                    )
                }
            }
    }

    override fun getAllStocksFromUserGrouped(userId: Long): Observable<List<GroupedStock>> {
        return stockDao.getAllStocksFromUserGrouped(userId)
    }

    override fun updateUserBalance(userId: Long, balance: Double, portfolioValue: Double): Completable {

        return userDao.updateUserBalance(userId, balance, portfolioValue)
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

    override fun insertStock(localStockEntity: LocalStockEntity): Completable {
        return stockDao.insertStock(localStockEntity)
    }

}