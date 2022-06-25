package com.example.projekat3.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.projekat3.data.models.stocks.LocalStock
import com.example.projekat3.databinding.LocalStockItemBinding

class LocalStockViewHolder (private val itemBinding: LocalStockItemBinding, val openDetailed: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener {
            openDetailed(layoutPosition)
        }
    }

    fun bind(localStock: LocalStock) {
        itemBinding.stockName.text = localStock.name
        itemBinding.numberOfTv.text = localStock.numberOf.toString()
        itemBinding.stockSymbol.text = localStock.symbol
    }

}