package com.anurag.newsfeedapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.data.News
import com.anurag.newsfeedapp.data.NewsFeedRepository
import com.anurag.newsfeedapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsListAdapter

    private val repository: NewsFeedRepository by lazy {
        NewsFeedRepository(
            newsFeedDao = (application as NewsFeedApplication).dataBase.newsFeedDao()
        )
    }

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

        lifecycleScope.launchWhenCreated {
            val newsResponse = repository.getNewsFeed(this@MainActivity.applicationContext)
            adapter.updateNews(newsResponse.news)
            newsResponse.errorMessage?.let {
                Toast.makeText(this@MainActivity, "Something went wrong - $it", Toast.LENGTH_LONG).show()
            }
        }
    }
}