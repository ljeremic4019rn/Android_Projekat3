package com.example.projekat3.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.presentation.view.states.StocksState

interface StocksContract {
    interface ViewModel {
        val stockState: LiveData<StocksState>
        val detailedStock: DetailedStock?
        fun fetchAllStocks(json: String)
        fun searchStock (json: String)

    }
}