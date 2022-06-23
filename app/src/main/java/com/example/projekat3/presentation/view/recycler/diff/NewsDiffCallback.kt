package com.example.projekat3.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat3.data.models.news.News

class NewsDiffCallback : DiffUtil.ItemCallback<News>(){

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.content == newItem.content &&
                oldItem.date == newItem.date &&
                oldItem.link == newItem.link &&
                oldItem.image == newItem.image
    }

}