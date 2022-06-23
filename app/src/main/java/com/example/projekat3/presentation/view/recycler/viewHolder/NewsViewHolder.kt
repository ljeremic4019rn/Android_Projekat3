package com.example.projekat3.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.projekat3.data.models.news.News
import com.example.projekat3.databinding.NewsItemBinding

class NewsViewHolder (private val itemBinding: NewsItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(news: News){
        itemBinding.titleTv.text = news.title
        itemBinding.dateTv.text = news.date
        //todo image
    }
}