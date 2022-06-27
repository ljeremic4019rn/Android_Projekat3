package com.example.projekat3.presentation.view.recycler.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.projekat3.data.models.news.News
import com.example.projekat3.databinding.NewsItemBinding
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsViewHolder (private val itemBinding: NewsItemBinding, val openLink: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root){

    init {
        itemBinding.root.setOnClickListener {
            openLink(layoutPosition)
        }
    }

    fun bind(news: News){
        itemBinding.titleTv.text = news.title
        itemBinding.dateTv.text = news.date.split("T")[0]
        Picasso
            .get()
            .load(news.image)
            .into(itemBinding.newsImageView)
    }
}