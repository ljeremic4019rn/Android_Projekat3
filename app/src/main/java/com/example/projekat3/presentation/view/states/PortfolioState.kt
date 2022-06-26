package com.example.projekat3.presentation.view.states

import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.data.models.stocks.LocalStock

sealed class PortfolioState {
    object DataFetched: PortfolioState()
    data class StockSuccess(val stocks: List<LocalStock>): PortfolioState()
    data class StockSuccessGrouped(val groupedStocks: List<GroupedStock>): PortfolioState()
    data class Error(val message: String): PortfolioState()
}