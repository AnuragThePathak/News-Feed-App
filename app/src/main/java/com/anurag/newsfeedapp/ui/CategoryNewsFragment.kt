package com.anurag.newsfeedapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.databinding.FragmentHomeBinding
import com.anurag.newsfeedapp.viewmodels.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryNewsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsListViewModel by viewModels()
    private val mAdapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeNews()
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = mAdapter
    }

    private fun observeNews() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsResponse.observe(viewLifecycleOwner) {
                mAdapter.submitList(it.news)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}