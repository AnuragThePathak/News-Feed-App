package com.anurag.newsfeedapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anurag.newsfeedapp.R
import com.anurag.newsfeedapp.adapters.CategoryListAdapter
import com.anurag.newsfeedapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val mAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter{
            val directions = ExploreFragmentDirections.actionNavExploreFragmentToNavCategoryNewsFragment(it)
            findNavController().navigate(directions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setCategories()
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = mAdapter
    }

    private fun setCategories() {
        val categories = resources.getStringArray(R.array.categories)
        mAdapter.submitList(categories.toList())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
