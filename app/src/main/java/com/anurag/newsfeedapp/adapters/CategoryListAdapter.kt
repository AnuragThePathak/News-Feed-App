package com.anurag.newsfeedapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.newsfeedapp.databinding.CategoryItemBinding

class CategoryListAdapter(
    private val listener: (String) -> Unit,
) : ListAdapter<String, CategoryListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) = binding.apply {
            categoryText.text = category
            categoryCard.setOnClickListener {
                listener.invoke(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemBinding.inflate(
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
