package com.example.projekat3.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat3.data.models.news.News
import com.example.projekat3.databinding.NewsItemBinding
import com.example.projekat3.presentation.view.recycler.diff.NewsDiffCallback
import com.example.projekat3.presentation.view.recycler.viewHolder.NewsViewHolder

class NewsAdapter : ListAdapter<News, NewsViewHolder>(NewsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}