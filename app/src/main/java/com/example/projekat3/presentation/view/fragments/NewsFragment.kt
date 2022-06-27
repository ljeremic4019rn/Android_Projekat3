package com.example.projekat3.presentation.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.projekat3.R
import com.example.projekat3.data.models.news.News
import com.example.projekat3.databinding.FragmentNewsBinding
import com.example.projekat3.presentation.contract.NewsContract
import com.example.projekat3.presentation.view.recycler.adapter.NewsAdapter
import com.example.projekat3.presentation.view.states.NewsState
import com.example.projekat3.presentation.viewModel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException
import java.io.InputStream

class NewsFragment : Fragment(R.layout.fragment_news){

    private val newsViewModel: NewsContract.ViewModel by sharedViewModel<NewsViewModel>()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.newsRv)
        binding.newsRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter = NewsAdapter(::openLink)
        binding.newsRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL))
        binding.newsRv.adapter = adapter
    }

    private fun openLink(news: News){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(news.link)
        startActivity(openURL)
    }

    private fun initObservers() {
        newsViewModel.newsState.observe(viewLifecycleOwner) { newsState ->
            renderState(newsState)
        }
        val myJson = activity?.resources?.openRawResource(R.raw.news)//ucitanvanje json-a
            ?.let {
                inputStreamToString(it)
            }
        if (myJson != null) {
            newsViewModel.fetchAllNews(myJson)
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

    private fun renderState(state: NewsState) {
        when (state) {
            is NewsState.Success -> {
                adapter.submitList(state.news)
            }
            is NewsState.Error -> {
                println("ERROR")

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