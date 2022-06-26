package com.example.projekat3.data.repositories

import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.user.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface PortfolioRepository {
    fun getUserByNameMailPass(username: String, email: String, password: String): Observable<UserEntity>
    fun insertUser(user: UserEntity): Completable

    fun insertStock(localStockEntity: LocalStockEntity): Completable
    fun getAllStocksFromUser(userId: Long): Observable<List<LocalStock>>
    fun getAllStocksFromUserGrouped(userId: Long): Observable<List<GroupedStock>>
    fun updateUserBalance (userId: Long, balance: Double, portfolioValue: Double): Completable
    fun searchStock (json: String): DetailedStock
}