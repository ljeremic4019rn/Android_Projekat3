package com.example.projekat3.presentation.view.recycler.viewHolder

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.StocksItemBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class StocksViewHolder (private val itemBinding: StocksItemBinding, val openDetailed: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root){

    init {
        itemBinding.root.setOnClickListener {
            openDetailed(layoutPosition)
        }
    }

    fun bind(stock: Stock){
        //ucitavanje grafa
        val entries: ArrayList<Entry> = ArrayList()
        var i = 0

        itemBinding.stockName.text = stock.name
        itemBinding.lastPrice.text = stock.last.toString()
        itemBinding.stockSymbol.text = stock.symbol
        itemBinding.chart1.setBackgroundColor(Color.WHITE)
        itemBinding.chart1.description.isEnabled = false
        itemBinding.chart1.setDrawGridBackground(false)
        itemBinding.chart1.setScaleEnabled(true)
        itemBinding.chart1.setPinchZoom(true)
        itemBinding.chart1.isDragEnabled = true

        stock.chart.bars.forEach {
            val value = it.price.toFloat()
            entries.add(Entry(i++.toFloat(), value))
        }

        val lineDataSet = LineDataSet(entries, "")
        if(stock.changeFromPreviousClose > 0){
            lineDataSet.color = Color.GREEN
        } else {
            lineDataSet.color = Color.RED
        }
        val data = LineData(lineDataSet)
        itemBinding.chart1.data = data
        itemBinding.chart1.invalidate()
    }





}