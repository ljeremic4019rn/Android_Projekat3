package com.example.projekat3.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.user.User
import com.example.projekat3.data.models.user.UserEntity
import com.example.projekat3.presentation.view.states.UserState
import io.reactivex.Completable
import io.reactivex.Observable

class UserContract {
    interface ViewModel {
        val userState: LiveData<UserState>
        val list : ArrayList<LocalStock>
        val user: User
        val detailedStock: DetailedStock?

        fun getAllStocksFromUser(userId: Long)
        fun insertUser(user: UserEntity)
        fun insertStock(localStockEntity: LocalStockEntity)
        fun getUserByNameMailPass(username: String, email: String, password: String)
        fun searchStock (json: String)
    }
}