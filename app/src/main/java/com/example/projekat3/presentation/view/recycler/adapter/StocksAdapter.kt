package com.example.projekat3.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.StocksItemBinding
import com.example.projekat3.presentation.view.recycler.diff.StocksDiffCallback
import com.example.projekat3.presentation.view.recycler.viewHolder.StocksViewHolder

class StocksAdapter(val openDetailed: (stock: Stock) -> Unit) : ListAdapter<Stock, StocksViewHolder>(StocksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val itemBinding = StocksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return StocksViewHolder(itemBinding)
        { openDetailed(getItem(it)) }
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}