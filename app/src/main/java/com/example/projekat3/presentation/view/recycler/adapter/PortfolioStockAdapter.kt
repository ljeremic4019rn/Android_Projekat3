package com.example.projekat3.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.databinding.LocalStockItemBinding
import com.example.projekat3.presentation.view.recycler.diff.PortfolioDiffCallback
import com.example.projekat3.presentation.view.recycler.viewHolder.PorftolioViewHolder

class PortfolioStockAdapter (val openDetailed: (stock: GroupedStock) -> Unit)  : ListAdapter<GroupedStock, PorftolioViewHolder>(PortfolioDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PorftolioViewHolder {
        val itemBinding = LocalStockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PorftolioViewHolder(itemBinding)
        { openDetailed(getItem(it)) }
    }

    override fun onBindViewHolder(holder: PorftolioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}