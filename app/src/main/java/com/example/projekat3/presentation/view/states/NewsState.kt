package com.example.projekat3.presentation.view.states

import com.example.projekat3.data.models.news.News

sealed class NewsState {
    object DataFetched: NewsState()
    data class Success(val news: List<News>): NewsState()
    data class Error(val message: String): NewsState()
}