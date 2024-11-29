package com.dicoding.asclepius.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ArticleItemBinding
import data.ArticlesItem

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesItem) {

            binding.articleTitle.text = article.title
            binding.articleDescription.text = article.description


            val imageUrl = article.urlToImage as? String
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.articleImage)


            binding.root.setOnClickListener {

            }
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
    override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
        return oldItem == newItem
    }
}