package com.anurag.newsfeedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsListAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val newsArray = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = newsArray[position].title
    }

    override fun getItemCount() = newsArray.size

    fun updateNews(updatedNews: ArrayList<News>) {
        newsArray.clear()
        newsArray += updatedNews

        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.text_view)
}

// We are using interface for this function because we need it in two functions
// without creating instances
