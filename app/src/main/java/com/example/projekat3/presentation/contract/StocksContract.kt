package com.example.projekat3.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat3.presentation.view.states.StocksState

interface StocksContract {
    interface ViewModel {
        val stockState: LiveData<StocksState>
        fun fetchAllStocks(json: String)
    }
}