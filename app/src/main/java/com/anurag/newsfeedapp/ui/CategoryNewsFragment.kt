package com.anurag.newsfeedapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.databinding.FragmentHomeBinding
import com.anurag.newsfeedapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryNewsFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val customIntent by lazy { CustomTabsIntent.Builder().build() }
    private val mAdapter: NewsListAdapter by lazy {
        NewsListAdapter({
            customIntent.launchUrl(requireContext(), Uri.parse(it))
        }, { newsUrl ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, newsUrl)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        })
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