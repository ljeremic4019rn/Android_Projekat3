package com.example.projekat3.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.fragment.app.Fragment
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.databinding.FragmentPortfolioBinding
import com.example.projekat3.presentation.contract.UserContract
import com.example.projekat3.presentation.view.activities.DetailedStockActivity
import com.example.projekat3.presentation.view.recycler.adapter.PortfolioStockAdapter
import com.example.projekat3.presentation.view.states.PortfolioState
import com.example.projekat3.presentation.viewModel.PortfolioViewModel
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import com.example.projekat3.data.models.stocks.GroupedStock
import java.util.*


class PortfolioFragment: Fragment(R.layout.fragment_portfolio)  {

    private val userViewModel: UserContract.ViewModel by sharedViewModel<PortfolioViewModel>()
    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PortfolioStockAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initRecycler()
        initObservers()
    }

    private fun initView(){
        binding.porfolioChart.setBackgroundColor(Color.WHITE)
        binding.porfolioChart.description.isEnabled = false
        binding.porfolioChart.setDrawGridBackground(false)
        binding.porfolioChart.isDragEnabled = true
        binding.porfolioChart.setScaleEnabled(true)
        binding.porfolioChart.setPinchZoom(true)

        val ourLineChartEntries: ArrayList<Entry> = ArrayList()
        var i = 0

        userViewModel.list.forEach {
            var value = it.value
            ourLineChartEntries.add(Entry(i++.toFloat(), value.toFloat() * -1))
        }

        val lineDataSet = LineDataSet(ourLineChartEntries, "")
        lineDataSet.color = Color.BLACK
        val data = LineData(lineDataSet)
        binding.porfolioChart.data = data
        binding.porfolioChart.invalidate()
}

    private fun initObservers() {
        userViewModel.portfolioState.observe(viewLifecycleOwner, Observer { newsState ->
            renderState(newsState)
        })
//        userViewModel.getAllStocksFromUser(userViewModel.user.id)
        userViewModel.getAllStocksFromUserGrouped(1)
    }

    private fun initRecycler(){
        adapter = PortfolioStockAdapter(::openDetailed)
        binding.userStocksRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.userStocksRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.userStocksRv.adapter = adapter
    }


    private fun openDetailed(localStock: GroupedStock){
        val myJson = activity?.resources?.openRawResource(R.raw.search_quote)
            ?.let { inputStreamToString(it) }
        if (myJson != null) {
            userViewModel.searchStock(myJson)
            startDetailedActivity(userViewModel.detailedStock)
        }
    }

    fun startDetailedActivity(detailedStock: DetailedStock?) {
        val intent = Intent(activity, DetailedStockActivity::class.java)
        intent.putExtra("detailedStock", detailedStock)
        intent.putExtra("numberOfOwned", 15)//todo ovaj broj povuci iz baze
        intent.putExtra("balance", userViewModel.user.balance)//todo ovaj broj povuci iz baze
        doAction.launch(intent)
    }

    private val doAction: ActivityResultLauncher<Intent> = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data

            val dateNow: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

            val numberOfBought = data?.getIntExtra("numberOfBought",0)
            val balanceSpent = data?.getDoubleExtra("balanceSpent",0.0)
            val name = data?.getStringExtra("name")
            val symbol = data?.getStringExtra("symbol")

            //ako je buy onda cemo da na bazu dodamo + value
            //ako je sell na bazu dodajemo - value
            if (name != null && symbol != null && numberOfBought != null && balanceSpent != null) {
                userViewModel.insertStock(
                    LocalStockEntity(
                        0,
                        userViewModel.user.id,
                        name,
                        numberOfBought,
                        symbol,
                        dateNow.toString(),
                        balanceSpent
                    )
                )
                userViewModel.user.balance += balanceSpent//todo ovo treba da ide u bazu a ne u lokalnu varijablu, uradi stura
                userViewModel.user.portfolioValue += balanceSpent//todo balance na bazi
            }
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String? {//za citanje json
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    private fun renderState(state: PortfolioState) {
        when (state) {
            is PortfolioState.StockSuccessGrouped -> {
                adapter.submitList(state.groupedStocks)
            }
            is PortfolioState.Error -> {
                println("ERROR")

                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is PortfolioState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}