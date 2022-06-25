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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.Stock
import com.example.projekat3.databinding.FragmentStocksBinding
import com.example.projekat3.presentation.contract.StocksContract
import com.example.projekat3.presentation.view.activities.DetailedStockActivity
import com.example.projekat3.presentation.view.recycler.adapter.StocksAdapter
import com.example.projekat3.presentation.view.states.StocksState
import com.example.projekat3.presentation.viewModel.StocksViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException
import java.io.InputStream

class StocksFragment : Fragment(R.layout.fragment_stocks){

    private val stockViewModel: StocksContract.ViewModel by sharedViewModel<StocksViewModel>()

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

    private fun initRecycler() {
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.stocksRv)
        adapter = StocksAdapter(::openDetailed)
        binding.stocksRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.stocksRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.stocksRv.adapter = adapter
    }

    private fun openDetailed(stock: Stock){
        val myJson = activity?.resources?.openRawResource(R.raw.search_quote)
            ?.let {
                inputStreamToString(it)
            }
        if (myJson != null) {
            stockViewModel.searchStock(myJson)
            startDetailedActivity(stockViewModel.detailedStock)

        }
    }

    fun startDetailedActivity(detailedStock: DetailedStock?) {
        val intent = Intent(activity, DetailedStockActivity::class.java)

        println("pre")
        println(detailedStock)

        intent.putExtra("detailedStock", detailedStock)
        doAction.launch(intent)
    }

    private val doAction: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
//            val detailedStock = data?.getStringExtra("detailedStock")
            println("GOTOVO JE")
//            if (true)
                TODO(/*ako bude trebalo stavi nesto*/)
//                noteViewModel.updateNote(id, title, content)
        }
    }


    private fun initObservers() {
        stockViewModel.stockState.observe(viewLifecycleOwner, Observer { stockState ->
            renderState(stockState)
        })
        val myJson = activity?.resources?.openRawResource(R.raw.indexes)
            ?.let {
                inputStreamToString(it)
            }
        if (myJson != null) {
            stockViewModel.fetchAllStocks(myJson)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}