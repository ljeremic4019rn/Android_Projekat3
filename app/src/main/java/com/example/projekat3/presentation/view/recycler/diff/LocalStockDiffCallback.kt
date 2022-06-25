package com.example.projekat3.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat3.data.models.stocks.LocalStock

class LocalStockDiffCallback: DiffUtil.ItemCallback<LocalStock>(){

    override fun areItemsTheSame(oldItem: LocalStock, newItem: LocalStock): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: LocalStock, newItem: LocalStock): Boolean {
        return  oldItem.userId == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.numberOf == newItem.numberOf &&
                oldItem.symbol == newItem.symbol &&
                oldItem.boughtDate == newItem.boughtDate &&
                oldItem.value == newItem.value
    }
}