package com.example.projekat3.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.StocksItemBinding
import com.example.projekat3.presentation.view.recycler.diff.StocksDiffCallback
import com.example.projekat3.presentation.view.recycler.viewHolder.StocksViewHolder

class StocksAdapter : ListAdapter<Stock, StocksViewHolder>(StocksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val itemBinding = StocksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StocksViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}