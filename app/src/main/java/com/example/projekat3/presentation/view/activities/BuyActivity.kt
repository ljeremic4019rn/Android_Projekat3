package com.example.projekat3.presentation.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock

import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.properties.Delegates

@SuppressLint("SetTextI18n")
class BuyActivity : AppCompatActivity() {

    lateinit var buyButton: Button
    lateinit var stockBuySwitch: SwitchMaterial
    lateinit var balance: TextView
    lateinit var stockName: TextView
    lateinit var number: EditText
    lateinit var mode: TextView

    lateinit var detailedStock: DetailedStock
    var balanceValue by Delegates.notNull<Double>()
    var last by Delegates.notNull<Double>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        if (intent.getParcelableExtra<DetailedStock>("detailedStock") != null) {
            detailedStock = intent.getParcelableExtra("detailedStock")!!
            balanceValue = intent.getDoubleExtra("balance", 0.0)
            last = intent.getDoubleExtra("last", 0.0)
        }

        initFields()
        setData()
        setListeners()
    }

    private fun initFields() {
        buyButton = findViewById<View>(R.id.buySharesButton) as Button
        stockBuySwitch = findViewById<View>(R.id.buySharesSwitch) as SwitchMaterial
        balance = findViewById<View>(R.id.buySharesBalance) as TextView
        stockName = findViewById<View>(R.id.buySharesName) as TextView
        number = findViewById<View>(R.id.buySharesNumber) as EditText
        mode = findViewById<View>(R.id.modeTv) as TextView
    }

    private fun setData() {
        stockName.text = detailedStock.name
        balance.text = balanceValue.toString()
        number.hint = "0"
        mode.text = "Input number of stock"
    }

    private fun setListeners() {
        stockBuySwitch.setOnClickListener {
            if (stockBuySwitch.isChecked) mode.text = "Input number of stock"
            else mode.text = "Input balance amount"
        }

        buyButton.setOnClickListener {
            //number of stock
            if (stockBuySwitch.isChecked) {
                if (balanceValue > Integer.parseInt(number.text.toString()) * last) {//ako je balans vece nego cena * broj kupljenih
                    val returnIntent = Intent()
                    returnIntent.putExtra("number", Integer.parseInt(number.text.toString()))
                    returnIntent.putExtra("mode", "number")
                    this.setResult(RESULT_OK, returnIntent)
                    this.finish()
                } else Toast.makeText(this,"You do not have enough balance",Toast.LENGTH_LONG).show()

            //balance
            } else {
                if (Integer.parseInt(number.text.toString()) <= balanceValue) {
                    val returnIntent = Intent()
                    returnIntent.putExtra("number", Integer.parseInt(number.text.toString()))
                    returnIntent.putExtra("mode", "balance")
                    this.setResult(RESULT_OK, returnIntent)
                    this.finish()
                } else Toast.makeText(this,"Please insert number lesser than $balanceValue",Toast.LENGTH_LONG).show()
            }
        }
    }

}

