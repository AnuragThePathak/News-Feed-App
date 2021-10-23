package com.anurag.newsfeedapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.newsfeedapp.data.News
import com.anurag.newsfeedapp.databinding.RowItemBinding
import com.bumptech.glide.Glide

class NewsListAdapter(private val newsItemCallback: NewsItemCallback) :
    ListAdapter<News, NewsListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<News>() {
            override fun areContentsTheSame(oldItem: News, newItem: News) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.uid == newItem.uid
        }
    }

    inner class ViewHolder(private val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) = binding.apply {
            titleView.text = news.title
            descriptionView.text = news.description
            sourceView.text = news.source
            timeView.text = news.time
            Glide.with(imageView.context).load(news.imageUrl)
                .centerCrop().into(imageView)
            shareBtn.setOnClickListener {
                newsItemCallback.onShareNewsClicked(news.url)
            }
            this.root.setOnClickListener {
                newsItemCallback.onNewsItemClicked(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface NewsItemCallback {
        fun onNewsItemClicked(news: News)

        fun onShareNewsClicked(url: String)
    }
}
