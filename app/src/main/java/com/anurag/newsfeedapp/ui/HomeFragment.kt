package com.anurag.newsfeedapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.anurag.newsfeedapp.R
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.databinding.FragmentHomeBinding
import com.anurag.newsfeedapp.viewmodels.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.nav_setting_fragment -> {
                findNavController().navigate(R.id.nav_setting_fragment)
                true
            }
            R.id.nav_notification_fragment -> {
                findNavController().navigate(R.id.nav_notification_fragment)
                true
            }
            else -> false
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
