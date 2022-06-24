package com.example.projekat3.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.StocksItemBinding

class StocksViewHolder (private val itemBinding: StocksItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(stock: Stock){
        itemBinding.stockName.text = stock.name
        itemBinding.lastPrice.text = stock.last.toString()
        itemBinding.stockSymbol.text = stock.symbol

    }

}