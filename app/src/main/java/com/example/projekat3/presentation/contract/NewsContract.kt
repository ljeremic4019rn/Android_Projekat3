package com.example.projekat3.presentation.contract

import androidx.lifecycle.LiveData
import com.example.projekat3.presentation.view.states.NewsState

interface NewsContract {
    interface ViewModel {
        val newsState: LiveData<NewsState>
        fun fetchAllNews(json: String)
    }
}