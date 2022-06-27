package com.example.projekat3.presentation.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat3.R
import kotlin.properties.Delegates

@SuppressLint("UseSwitchCompatOrMaterialCode")
class SellActivity : AppCompatActivity(){

    private lateinit var stockName: TextView
    private lateinit var stockSellSwitch: Switch
    private lateinit var stockAmountToSell: EditText
    private lateinit var sellButton: Button
    private var numberOfOwned by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        numberOfOwned =  intent.getIntExtra("numberOfOwned", 0)

        initFields()
        setData()
        setListeners()
    }

    private fun initFields() {
        stockName = findViewById<View>(R.id.sellNameTv) as TextView
        stockSellSwitch = findViewById<View>(R.id.sellSwitch) as Switch
        stockAmountToSell = findViewById<View>(R.id.sellNumber) as EditText
        sellButton = findViewById<View>(R.id.sellButton) as Button
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        stockName.text =  intent.getStringExtra("name")
        stockSellSwitch.text =  "Sell all " + intent.getStringExtra("symbol") + " stocks"
        stockAmountToSell.hint = "0"
    }


    /*
    switch menja input field izmedju editable i read only (stavi numberOfOwned)
    ako se upise rec umesto broja moze da pravi probleme ali bi trebalo da se dobije 0 kao fail safe
     */

    private fun setListeners() {
        stockSellSwitch.setOnClickListener {
            if (stockSellSwitch.isChecked) {
                stockAmountToSell.setText(numberOfOwned.toString())
                stockAmountToSell.isEnabled = false
            } else {
                stockAmountToSell.isEnabled = true
            }
        }

        sellButton.setOnClickListener {
            val numberOfSold = Integer.parseInt(stockAmountToSell.text.toString())

            if (numberOfSold in 0..numberOfOwned) {//ako je broj izmedju 0 i koliko imamo, mozemo taj broj deonica  da prodamo
                val returnIntent = Intent()
                returnIntent.putExtra("numberOfSold", numberOfSold)
                this.setResult(RESULT_OK, returnIntent)
                this.finish()
            }
            else Toast.makeText(this, "Please insert number 0 < X < $numberOfOwned", Toast.LENGTH_LONG).show()
        }
    }

}