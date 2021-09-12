package com.anurag.newsfeedapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsListAdapter()
        binding.recyclerView.adapter = adapter
        fetchData()

    }

    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    newsArray += News(
                        jsonObject.getString("title"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage"),
                        jsonObject.getString("description"),
                        jsonObject.getJSONObject("source")
                            .getString("name")
                    )
                }

                adapter.updateNews(newsArray)
            },
            {
                Toast.makeText(
                    this, "Something went wrong",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}