package com.anurag.newsfeedapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anurag.newsfeedapp.R
import com.anurag.newsfeedapp.data.News
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val newsArray = ArrayList<News>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title_view)
        val descriptionView: TextView = view.findViewById(R.id.description_view)
        val sourceView: TextView = view.findViewById(R.id.source_view)
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val timeView: TextView = view.findViewById(R.id.time_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener(newsArray[viewHolder.adapterPosition].url)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = newsArray[position].title
        holder.descriptionView.text = newsArray[position].description
        holder.sourceView.text = newsArray[position].source
        holder.timeView.text = newsArray[position].time
        Glide.with(holder.imageView.context).load(newsArray[position].imageUrl)
            .centerCrop().into(holder.imageView)
    }

    override fun getItemCount() = newsArray.size

    fun updateNews(updatedNews: List<News>) {
        newsArray.clear()
        newsArray += updatedNews

        notifyDataSetChanged()
    }
}