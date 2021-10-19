package com.anurag.newsfeedapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.databinding.ActivityMainBinding
import com.anurag.newsfeedapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Latest News"

        adapter = NewsListAdapter { url ->
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }

        binding.recyclerView.adapter = adapter

        viewModel.newsResponse.observe(this, { newsResponse ->
            adapter.updateNews(newsResponse.news)

            newsResponse.errorMessage?.let {
                Toast.makeText(
                    this@MainActivity,
                    "Something went wrong", Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}