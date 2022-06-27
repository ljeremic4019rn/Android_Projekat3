package com.example.projekat3.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.FragmentStocksBinding
import com.example.projekat3.presentation.contract.PortfolioContract
import com.example.projekat3.presentation.contract.StocksContract
import com.example.projekat3.presentation.view.activities.DetailedStockActivity
import com.example.projekat3.presentation.view.recycler.adapter.StocksAdapter
import com.example.projekat3.presentation.view.states.StocksState
import com.example.projekat3.presentation.viewModel.PortfolioViewModel
import com.example.projekat3.presentation.viewModel.StocksViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class StocksFragment : Fragment(R.layout.fragment_stocks){

    private val stockViewModel: StocksContract.ViewModel by sharedViewModel<StocksViewModel>()
    private val portfolioViewModel: PortfolioContract.ViewModel by sharedViewModel<PortfolioViewModel>()

    private var _binding: FragmentStocksBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StocksAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        stockViewModel.stockState.observe(viewLifecycleOwner) { stockState ->
            renderState(stockState)
        }
        val myJson = activity?.resources?.openRawResource(R.raw.indexes)
            ?.let { inputStreamToString(it) }
        if (myJson != null) {
            stockViewModel.fetchAllStocks(myJson)
        }
    }

    private fun initRecycler() {
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.stocksRv)
        adapter = StocksAdapter(::openDetailed)
        binding.stocksRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.stocksRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.stocksRv.adapter = adapter
    }

    private fun openDetailed(stock: Stock){
        val myJson = activity?.resources?.openRawResource(R.raw.search_quote)//ucitavamo json file jer ne mozemo sa online baze
            ?.let {
                inputStreamToString(it)
            }
        if (myJson != null) {
            stockViewModel.searchStock(myJson)
            startDetailedActivity(stockViewModel.detailedStock)
        }
    }

    /*
    objasnjenje za rad ovoga je napisano u PortfolioFragment
    rad ove funkcije je identican !
     */
    private fun startDetailedActivity(detailedStock: DetailedStock?) {
        val intent = Intent(activity, DetailedStockActivity::class.java)
        intent.putExtra("detailedStock", detailedStock)
        intent.putExtra("numberOfOwned",getAmountOfClickedStock(detailedStock!!.name))//todo ovaj broj povuci iz baze
        intent.putExtra("balance", portfolioViewModel.user.value!!.balance)
        doAction.launch(intent)
    }

    private val doAction: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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


    private fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    private fun renderState(state: StocksState) {
        when (state) {
            is StocksState.Success -> {
                adapter.submitList(state.stocks)
            }
            is StocksState.Error -> {
                println("ERROR")
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is StocksState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getAmountOfClickedStock(name: String): Int{
        portfolioViewModel.amountOfOwned.forEach{
            if (it.name == name) return it.sum
        }
        return 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}