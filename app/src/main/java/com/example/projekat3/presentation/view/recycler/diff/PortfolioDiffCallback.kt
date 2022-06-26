package com.example.projekat3.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat3.data.models.stocks.GroupedStock

class PortfolioDiffCallback: DiffUtil.ItemCallback<GroupedStock>(){

    override fun areItemsTheSame(oldItem: GroupedStock, newItem: GroupedStock): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: GroupedStock, newItem: GroupedStock): Boolean {
        return  oldItem.name == newItem.name &&
                oldItem.sum == newItem.sum &&
                oldItem.symbol == newItem.symbol

    }
}