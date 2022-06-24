package com.example.projekat3.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat3.data.models.stocks.Stock

class StocksDiffCallback : DiffUtil.ItemCallback<Stock>() {

    override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.symbol == newItem.symbol &&
                oldItem.currency == newItem.currency &&
                oldItem.last == newItem.last &&
                oldItem.changeFromPreviousClose == newItem.changeFromPreviousClose &&
                oldItem.percentChangeFromPreviousClose == newItem.percentChangeFromPreviousClose &&
                oldItem.marketName == newItem.marketName &&
                oldItem.recommendation == newItem.recommendation &&
                oldItem.chart == newItem.chart
    }

}
