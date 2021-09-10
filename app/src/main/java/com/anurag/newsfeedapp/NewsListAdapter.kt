package com.anurag.newsfeedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsListAdapter(private val dataset: ArrayList<String>, private val listener: ItemTapped) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onRecyclerTapped(dataset[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }

    override fun getItemCount() = dataset.size
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.id_of_text_view)
}

// We are using interface for this function because we need it in two functions
// without creating instances
interface ItemTapped {
    fun onRecyclerTapped(item: String)
}