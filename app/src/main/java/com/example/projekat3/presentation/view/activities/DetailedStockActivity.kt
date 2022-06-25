package com.example.projekat3.presentation.view.activities


import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.Chart
import com.example.projekat3.data.models.stocks.DetailedStock
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import timber.log.Timber

class DetailedStockActivity : AppCompatActivity() {

    lateinit var buyButton: Button
    lateinit var sellButton: Button
    lateinit var stockSymbol: TextView
    lateinit var stockValue: TextView
    lateinit var chart: LineChart
    lateinit var mktCap: TextView
    lateinit var open: TextView
    lateinit var bid: TextView
    lateinit var close: TextView
    lateinit var ask: TextView
    lateinit var divYield: TextView
    lateinit var pe: TextView
    lateinit var eps: TextView
    lateinit var ebit: TextView
    lateinit var beta: TextView

    lateinit var detailedStock: DetailedStock


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_stock)

        if (intent.getParcelableExtra<DetailedStock>("detailedStock") != null) {
            detailedStock = intent.getParcelableExtra<DetailedStock>("detailedStock")!!

        } else {
            Toast.makeText(this, "Error while loading stock", Toast.LENGTH_LONG).show()
            return
        }

        initFields()
        setData()
    }

    private fun initFields() {
        buyButton = findViewById<View>(R.id.buyButton) as Button
        sellButton = findViewById<View>(R.id.sellButton) as Button

        stockSymbol = findViewById<View>(R.id.stockSymbolDetails) as TextView
        stockValue = findViewById<View>(R.id.stockValueDetails) as TextView

        chart = findViewById<View>(R.id.stockDetailsChart) as LineChart

        mktCap = findViewById<View>(R.id.mktCapTextView) as TextView
        open = findViewById<View>(R.id.openTextView) as TextView
        bid = findViewById<View>(R.id.bidTextView) as TextView
        close = findViewById<View>(R.id.closeTextView) as TextView
        ask = findViewById<View>(R.id.askTextView) as TextView
        divYield = findViewById<View>(R.id.divYieldTextView) as TextView
        pe = findViewById<View>(R.id.peTextView) as TextView
        eps = findViewById<View>(R.id.epsTextView) as TextView
        ebit = findViewById<View>(R.id.ebitTextView) as TextView
        beta = findViewById<View>(R.id.betaTextView) as TextView
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        stockSymbol.text = "symbol: " + detailedStock.symbol
        stockValue.text = "value: " +detailedStock.last.toString()


        mktCap.text ="mtkCap: " + detailedStock.metrics.marketCup.toString()
        open.text = "open: " + detailedStock.open.toString()
        bid.text ="bid: " + detailedStock.bid.toString()
        close.text ="close: " + detailedStock.close.toString()
        ask.text ="ask: " + detailedStock.ask.toString()
        divYield.text  = ""
        pe.text ="pe: " + (detailedStock.last / detailedStock.metrics.marketCup).toString()
        eps.text ="eps: " + detailedStock.metrics.eps.toString()
        ebit.text ="ebit: " + detailedStock.metrics.ebit.toString()
        beta.text ="beta: " + detailedStock.metrics.beta.toString()


        //chart value
        val ourLineChartEntries: ArrayList<Entry> = ArrayList()
        var i = 0

        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        detailedStock.chart.bars.forEach {
            val value = it.price.toFloat()
            ourLineChartEntries.add(Entry(i++.toFloat(), value))
        }

        val lineDataSet = LineDataSet(ourLineChartEntries, "")
        lineDataSet.color = Color.BLACK
        val data = LineData(lineDataSet)
        chart.data = data
        chart.invalidate()
    }

    private fun setListeners() {
        buyButton.setOnClickListener {

        }

        sellButton.setOnClickListener {

        }
    }


}