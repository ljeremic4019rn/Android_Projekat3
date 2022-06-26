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
import androidx.appcompat.app.AppCompatActivity
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
import com.example.projekat3.presentation.contract.PortfolioContract
import com.example.projekat3.presentation.view.activities.DetailedStockActivity
import com.example.projekat3.presentation.view.recycler.adapter.PortfolioStockAdapter
import com.example.projekat3.presentation.view.states.PortfolioState
import com.example.projekat3.presentation.viewModel.PortfolioViewModel
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.ArrayList
import com.example.projekat3.data.models.stocks.GroupedStock
import java.util.*


class PortfolioFragment: Fragment(R.layout.fragment_portfolio)  {

    private val portfolioViewModel: PortfolioContract.ViewModel by sharedViewModel<PortfolioViewModel>()
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
        loadUser()
    }

    private fun initView(){
        binding.porfolioChart.setBackgroundColor(Color.WHITE)
        binding.porfolioChart.description.isEnabled = false
        binding.porfolioChart.setDrawGridBackground(false)
        binding.porfolioChart.isDragEnabled = true
        binding.porfolioChart.setScaleEnabled(true)
        binding.porfolioChart.setPinchZoom(true)
}



    private fun loadUser(){//todo ako je nov dodaj ga u bazu
        val sharedPreferences = activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)

        val username = sharedPreferences?.getString("username", "").toString()
        val email = sharedPreferences?.getString("email", "").toString()
        val password = sharedPreferences?.getString("password", "").toString()

        portfolioViewModel.getUserByNameMailPass(username, email, password)
    }


    private fun initObservers() {
        portfolioViewModel.portfolioState.observe(viewLifecycleOwner) { newsState ->
            renderState(newsState)
        }

        portfolioViewModel.user.observe(viewLifecycleOwner) {
            if (portfolioViewModel.user.value?.id == 0L) Toast.makeText(context, "Please login as existing user", Toast.LENGTH_SHORT).show()

            portfolioViewModel.getAllStocksFromUserGrouped(portfolioViewModel.user.value!!.id)
            binding.userBalance.text = portfolioViewModel.user.value!!.balance.toString()
            binding.userPortfolio.text = portfolioViewModel.user.value!!.portfolioValue.toString()
        }

        val ourLineChartEntries: ArrayList<Entry> = ArrayList()
        var i = 0
        var initialPortfolio = 0.0

        portfolioViewModel.userStocks.observe(viewLifecycleOwner) {
            ourLineChartEntries.clear()
            binding.porfolioChart.invalidate()
            binding.porfolioChart.clear()
            println(portfolioViewModel.userStocks.value)
            portfolioViewModel.userStocks.value?.forEach {
                val value = it.value
                initialPortfolio += value * -1

                ourLineChartEntries.add(Entry(i++.toFloat(), initialPortfolio.toFloat()))

                val lineDataSet = LineDataSet(ourLineChartEntries, "")
                lineDataSet.color = Color.BLACK
                val data = LineData(lineDataSet)
                binding.porfolioChart.data = data
                binding.porfolioChart.invalidate()
            }

            if (portfolioViewModel.user.value?.id == 0L) {
                binding.userBalance.text = portfolioViewModel.user.value!!.balance.toString()
                binding.userPortfolio.text = portfolioViewModel.user.value!!.portfolioValue.toString()
            }
        }

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
            portfolioViewModel.searchStock(myJson)
            startDetailedActivity(portfolioViewModel.detailedStock)
        }
    }

    fun startDetailedActivity(detailedStock: DetailedStock?) {
        val intent = Intent(activity, DetailedStockActivity::class.java)
        intent.putExtra("detailedStock", detailedStock)
        intent.putExtra("numberOfOwned", 15)//todo ovaj broj povuci iz baze
        intent.putExtra("balance", portfolioViewModel.user.value!!.balance)//todo ovaj broj povuci iz baze
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
                portfolioViewModel.insertStock(
                    LocalStockEntity(
                        0,
                        portfolioViewModel.user.value!!.id,
                        name,
                        numberOfBought,
                        symbol,
                        dateNow.toString(),
                        balanceSpent
                    )
                )
                portfolioViewModel.user.value!!.balance += balanceSpent
                portfolioViewModel.user.value!!.portfolioValue += balanceSpent * -1

                portfolioViewModel.updateUserBalance(
                    portfolioViewModel.user.value!!.id,
                    portfolioViewModel.user.value!!.balance,
                    portfolioViewModel.user.value!!.portfolioValue )
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