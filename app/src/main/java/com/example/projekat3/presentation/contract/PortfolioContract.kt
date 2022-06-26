package com.example.projekat3.presentation.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.user.User
import com.example.projekat3.data.models.user.UserEntity
import com.example.projekat3.presentation.view.states.PortfolioState

interface PortfolioContract {
    interface ViewModel {
        val portfolioState: LiveData<PortfolioState>
        val user: MutableLiveData<User>
        val detailedStock: DetailedStock?
        val userStocks: LiveData<List<LocalStock>>


        fun getAllStocksFromUser(userId: Long)
        fun getAllStocksFromUserGrouped(userId: Long)
        fun insertUser(user: UserEntity)
        fun updateUserBalance(userId: Long, balance: Double, portfolioValue: Double)
        fun insertStock(localStockEntity: LocalStockEntity)
        fun getUserByNameMailPass(username: String, email: String, password: String)
        fun searchStock (json: String)
    }
}