package com.example.projekat3.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.LocalStockItemBinding
import com.example.projekat3.presentation.view.recycler.diff.LocalStockDiffCallback
import com.example.projekat3.presentation.view.recycler.viewHolder.LocalStockViewHolder

class LocalStockAdapter (val openDetailed: (stock: LocalStock) -> Unit)  : ListAdapter<LocalStock, LocalStockViewHolder>(LocalStockDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalStockViewHolder {
        val itemBinding = LocalStockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocalStockViewHolder(itemBinding)
        { openDetailed(getItem(it)) }
    }

    override fun onBindViewHolder(holder: LocalStockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}