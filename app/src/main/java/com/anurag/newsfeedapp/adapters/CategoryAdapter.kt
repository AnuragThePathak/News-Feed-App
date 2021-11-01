package com.anurag.newsfeedapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anurag.newsfeedapp.databinding.CategoryItemBinding
import com.anurag.newsfeedapp.ui.ExploreFragmentDirections

class CategoryAdapter(
    private val categories: List<String>,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                navigateToCategoryNews(categories[adapterPosition], it)
            }
        }

        private fun navigateToCategoryNews(category: String, view: View) {
            val action = ExploreFragmentDirections
                .actionExploreFragmentToCategoryNewsFragment(category)

            view.findNavController().navigate(action)
        }

        fun bind(category: String) = binding.apply {
            categoryText.text = category
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
        if (position == RecyclerView.NO_POSITION) return
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}
