package com.example.projekat3.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat3.R
import com.example.projekat3.databinding.FragmentNewsBinding
import com.example.projekat3.presentation.contract.NewsContract
import com.example.projekat3.presentation.view.recycler.adapter.NewsAdapter
import com.example.projekat3.presentation.view.states.NewsState
import com.example.projekat3.presentation.viewModel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class NewsFragment : Fragment(R.layout.fragment_news){

    private val newsViewModel: NewsContract.ViewModel by sharedViewModel<NewsViewModel>()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
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
        binding.newsRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter = NewsAdapter()
        binding.newsRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.newsRv.adapter = adapter
    }


    private fun initObservers() {
        newsViewModel.newsState.observe(viewLifecycleOwner, Observer { newsState ->
            Timber.e(newsState.toString())
            renderState(newsState)
        })
        newsViewModel.fetchAllNews()
    }

    private fun renderState(state: NewsState) {
        when (state) {
            is NewsState.Success -> {
                adapter.submitList(state.courses)
            }
            is NewsState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is NewsState.DataFetched -> {
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