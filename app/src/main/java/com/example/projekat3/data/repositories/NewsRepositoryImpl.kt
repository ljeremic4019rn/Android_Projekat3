package com.example.projekat3.data.repositories

import com.example.projekat3.data.datasource.remote.NewsService
import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.news.NewsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.util.concurrent.atomic.AtomicLong

class NewsRepositoryImpl(private val remoteDataSource: NewsService) : NewsRepository {

    private var idCounter: AtomicLong = AtomicLong(0)

    override fun fetchAll(json: String): Observable<List<News>> {

        val gson = Gson()
        val newsType = object : TypeToken<List<NewsResponse>>() {}.type
        val news: List<NewsResponse> = gson.fromJson(json, newsType)
        val newsObservable = Observable.fromArray(news)

        return newsObservable.map {
            it.map { news: NewsResponse ->
                News(
                    id = idCounter.getAndIncrement(),
                    title = news.title,
                    content = news.title,
                    link = news.link,
                    date = news.date,
                    image = news.image
                )
            }
        }
    }
}