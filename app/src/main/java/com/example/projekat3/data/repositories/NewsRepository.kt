package com.example.projekat3.data.repositories

import com.example.projekat3.data.models.news.Resource
import io.reactivex.Observable

interface NewsRepository {
    fun fetchAll(): Observable<Resource<Unit>>
}