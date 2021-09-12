package com.anurag.newsfeedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val newsArray = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = newsArray[position].title
        holder.descriptionView.text = newsArray[position].description
        holder.sourceView.text = newsArray[position].source
        Glide.with(holder.imageView.context).load(newsArray[position].imageUrl)
            .centerCrop().into(holder.imageView)
    }

    override fun getItemCount() = newsArray.size

    fun updateNews(updatedNews: ArrayList<News>) {
        newsArray.clear()
        newsArray += updatedNews

        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleView: TextView = view.findViewById(R.id.title_view)
    val descriptionView: TextView = view.findViewById(R.id.description_view)
    val sourceView: TextView = view.findViewById(R.id.source_view)
    val imageView: ImageView = view.findViewById(R.id.image_view)
}

// We are using interface for this function because we need it in two functions
// without creating instances
