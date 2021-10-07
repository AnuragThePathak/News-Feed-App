package com.anurag.newsfeedapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.lifecycleScope
import com.anurag.newsfeedapp.adapters.NewsListAdapter
import com.anurag.newsfeedapp.adapters.OnRecyclerTap
import com.anurag.newsfeedapp.data.News
import com.anurag.newsfeedapp.data.NewsFeedRepository
import com.anurag.newsfeedapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), OnRecyclerTap {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsListAdapter

    private val repo: NewsFeedRepository = NewsFeedRepository(
        onSuccess = {
            adapter.updateNews(it)
            storeToDB(it)
        }, onFailure = {
            Toast.makeText(
                this, "Something went wrong - $it",
                Toast.LENGTH_LONG
            ).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Latest News"

        adapter = NewsListAdapter(this)
        binding.recyclerView.adapter = adapter

        repo.connectDatabase((application as NewsFeedApplication).database)
        repo.getNewsFeed(this)
    }

    private fun storeToDB(items: List<News>){
        lifecycleScope.launch(Dispatchers.IO) {
            repo.storeAllNewsItems(items)
        }
    }

    override fun onClickNew(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}