package com.example.projekat3.presentation.view.activities


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DetailedStockActivity : AppCompatActivity() {

    private lateinit var buyButton: Button
    private lateinit var sellButton: Button
    private lateinit var stockSymbol: TextView
    private lateinit var stockValue: TextView
    private lateinit var chart: LineChart
    private lateinit var mktCap: TextView
    private lateinit var open: TextView
    private lateinit var bid: TextView
    private lateinit var close: TextView
    private lateinit var ask: TextView
    private lateinit var divYield: TextView
    private lateinit var pe: TextView
    private lateinit var eps: TextView
    private lateinit var ebit: TextView
    private lateinit var beta: TextView
    private lateinit var detailedStock: DetailedStock

    private var balance = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_stock)

        if (intent.getParcelableExtra<DetailedStock>("detailedStock") != null) {//uzimamo podatke koji su nam potrebni za obradu
            detailedStock = intent.getParcelableExtra("detailedStock")!!
            balance = intent.getDoubleExtra("balance", 0.0)
        } else {
            Toast.makeText(this, "Error while loading stock", Toast.LENGTH_LONG).show()
            return
        }

        initFields()
        setData()
        setListeners()
    }

    private fun initFields() {
        buyButton = findViewById<View>(R.id.buyButton) as Button
        sellButton = findViewById<View>(R.id.sellButton) as Button

        stockSymbol = findViewById<View>(R.id.stockSymbolDetails) as TextView
        stockValue = findViewById<View>(R.id.stockValueDetails) as TextView

        chart = findViewById<View>(R.id.stockDetailsChart) as LineChart

        mktCap = findViewById<View>(R.id.mktCapTv) as TextView
        open = findViewById<View>(R.id.openTv) as TextView
        bid = findViewById<View>(R.id.bidTextView) as TextView
        close = findViewById<View>(R.id.closeTextView) as TextView
        ask = findViewById<View>(R.id.askTv) as TextView
        divYield = findViewById<View>(R.id.divYieldTv) as TextView
        pe = findViewById<View>(R.id.peTv) as TextView
        eps = findViewById<View>(R.id.epsTv) as TextView
        ebit = findViewById<View>(R.id.ebitTv) as TextView
        beta = findViewById<View>(R.id.betaTv) as TextView
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        stockSymbol.text = "symbol: " + detailedStock.symbol
        stockValue.text = "value: " + detailedStock.last.toString()

        mktCap.text = "mtkCap: " + detailedStock.metrics.marketCup.toString()
        open.text = "open: " + detailedStock.open.toString()
        bid.text = "bid: " + detailedStock.bid.toString()
        close.text = "close: " + detailedStock.close.toString()
        ask.text = "ask: " + detailedStock.ask.toString()
        divYield.text = ""
        pe.text = "pe: " + (detailedStock.last / detailedStock.metrics.marketCup).toString()
        eps.text = "eps: " + detailedStock.metrics.eps.toString()
        ebit.text = "ebit: " + detailedStock.metrics.ebit.toString()
        beta.text = "beta: " + detailedStock.metrics.beta.toString()

        //podesavamo chart prema podacima
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
            startBuyActivity()
        }

        sellButton.setOnClickListener {
            startSellActivity()
        }
    }

    //SELL ACTIVITY
    private fun startSellActivity() {
        val intent = Intent(this, SellActivity::class.java)
        intent.putExtra("name", detailedStock.name)
        intent.putExtra("symbol", detailedStock.name)
        intent.putExtra("numberOfOwned", this.intent.getIntExtra("numberOfOwned", 0))//limit koliko mozemo da prodamo
        doSellAction.launch(intent)
    }

    /*
     primimo broj deonica koji smo uneli za prodaju numberOfSold
     mnozimo ga sa balansom usera da dobijemo profit
     numberOfSold * -1 da bi se oduzelo pri sabiranju kada budemo prolazili kroz listu
     zapakujemo i posaljemo nazad
     */
    private val doSellAction: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data

            val numberOfSold = data?.getIntExtra("numberOfSold",0)!!
            val balanceGained = numberOfSold.times(detailedStock.last)

            val returnIntent = Intent()
            returnIntent.putExtra("numberOfBought", numberOfSold * -1)
            returnIntent.putExtra("balanceSpent", balanceGained)
            returnIntent.putExtra("name", detailedStock.name)
            returnIntent.putExtra("symbol", detailedStock.symbol)
            this.setResult(RESULT_OK, returnIntent)
            this.finish()
        }
    }

    //BUY ACTIVITY
    private fun startBuyActivity() {
        val intent = Intent(this, BuyActivity::class.java)
        intent.putExtra("detailedStock", detailedStock)
        intent.putExtra("balance", balance)
        intent.putExtra("last", detailedStock.last)
        doBuyAction.launch(intent)
    }

    /*
    -BALANCE-
        primimo broj unetog novca
        podelimo ga sa cenom deonice da znamo koliko smo kupili
        pomnozoimo broj kupljenih, sa  cenom deonice -> da bi dobili pravu cenu (da ne bi skinuli 1000 ako je kostalo 998)
        zapakujemo i posaljemo nazad

   -NUMBER-
        broj kupljenih pomnozimo sa cenom deonice
        zapakujemo i posaljemo

        balance * -1 da bi oduzimali balance pri prolasku kroz listu
     */
    private val doBuyAction: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data

            var numberOfBought = 0
            var balanceSpent = 0.0

            if (data?.getStringExtra("mode") == "balance") {
                val balanceEntered = data.getIntExtra("number", 0)
                numberOfBought = (balanceEntered / detailedStock.last).toInt()
                balanceSpent  = numberOfBought * detailedStock.last
            }
            else if (data?.getStringExtra("mode") == "number") {
                numberOfBought = data.getIntExtra("number", 0)
                balanceSpent = numberOfBought * detailedStock.last
            }

            val returnIntent = Intent()
            returnIntent.putExtra("numberOfBought", numberOfBought)
            returnIntent.putExtra("balanceSpent", balanceSpent * -1)
            returnIntent.putExtra("name", detailedStock.name)
            returnIntent.putExtra("symbol", detailedStock.symbol)
            this.setResult(RESULT_OK, returnIntent)
            this.finish()
        }
    }

}