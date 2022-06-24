package com.example.projekat3.data.models.news

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NewsResponse(
    val title: String,
    val content: String,
    val link: String,
    val date: String,
    val image: String
)