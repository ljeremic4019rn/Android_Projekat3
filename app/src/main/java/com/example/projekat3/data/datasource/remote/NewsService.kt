package com.example.projekat3.data.datasource.remote

import com.example.projekat3.data.models.news.News
import com.example.projekat3.data.models.news.NewsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsService {
    @GET("raspored/json.php")//todo fix
    fun fetchAll(): Observable<List<NewsResponse>>
}