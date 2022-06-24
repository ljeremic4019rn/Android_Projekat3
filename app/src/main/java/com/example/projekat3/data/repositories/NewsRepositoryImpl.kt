package com.example.projekat3.data.repositories

import com.example.projekat3.data.datasource.remote.NewsService
import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.news.NewsResponse
import com.example.projekat3.data.models.news.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class NewsRepositoryImpl(private val remoteDataSource: NewsService) : NewsRepository {

    private var idCounter: AtomicLong = AtomicLong(0)

    override fun fetchAll(json: String): Observable<List<News>> {

        val gson = Gson()
        val listNewsType = object : TypeToken<List<NewsResponse>>() {}.type

        val news: List<NewsResponse> = gson.fromJson(json, listNewsType)
        val newsObservable = Observable.fromArray(news)

        return newsObservable.map {
            it.map { newsResponse: NewsResponse ->
                News(
                    id = idCounter.getAndIncrement(),
                    title = newsResponse.title,
                    content = newsResponse.title,
                    link = newsResponse.link,
                    date = newsResponse.date,
                    image = newsResponse.image
                )
            }
        }
    }
}