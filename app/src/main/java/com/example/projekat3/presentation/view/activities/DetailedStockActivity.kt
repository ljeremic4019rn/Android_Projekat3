package com.example.projekat3.presentation.view.activities


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

    private fun setData() {

        stockSymbol.text = detailedStock.symbol
        stockValue.text = detailedStock.name

//        chart = detailedStock.chart

        mktCap
        open
        bid
        close
        ask
        divYield
        pe
        eps
        ebit
        beta.text = detailedStock.metrics.beta.toString()
    }

    private fun setListeners() {
        buyButton.setOnClickListener {

        }

        sellButton.setOnClickListener {

        }
    }


}