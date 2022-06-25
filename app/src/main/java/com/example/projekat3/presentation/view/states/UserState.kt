package com.example.projekat3.presentation.view.states

import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.user.User

sealed class UserState {
    object Loading: UserState()
    object DataFetched: UserState()
    data class StockSuccess(val stocks: List<LocalStock>): UserState()
    data class Error(val message: String): UserState()
}