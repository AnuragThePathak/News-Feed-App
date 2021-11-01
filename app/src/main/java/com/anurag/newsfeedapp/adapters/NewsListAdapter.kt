package com.anurag.newsfeedapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.newsfeedapp.data.News
import com.anurag.newsfeedapp.databinding.NewsItemBinding
import com.bumptech.glide.Glide


class NewsListAdapter : ListAdapter<News, NewsListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<News>() {
            override fun areContentsTheSame(oldItem: News, newItem: News) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.uid == newItem.uid
        }
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                CustomTabsIntent.Builder().build()
                    .launchUrl(it.context, Uri.parse(getItem(adapterPosition).url))
            }

            binding.shareButton.setOnClickListener {
                shareLink(getItem(adapterPosition).url, it)
            }
        }

        private fun shareLink(url: String, view: View) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            view.context.startActivity(shareIntent)
        }

        fun bind(news: News) = binding.apply {
            titleView.text = news.title
            descriptionView.text = news.description
            sourceView.text = news.source
            timeView.text = news.time
            Glide.with(imageView.context).load(news.imageUrl)
                .centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
