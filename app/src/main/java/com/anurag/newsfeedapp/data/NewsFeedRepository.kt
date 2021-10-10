package com.anurag.newsfeedapp.data

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.MySingleton

class NewsFeedRepository(
    private val newsFeedDao: NewsFeedDao,
    onSuccess: (ArrayList<News>) -> Unit,
    onFailure: (String?) -> Unit
) {
    @SuppressLint("NewApi")
    private val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, URL, null,
        { response ->
            val jsonArray = response.getJSONArray("articles")
            val newsArray = ArrayList<News>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val publishedAt = jsonObject.getString("publishedAt")      //fetching publishedAt from the JSON file

                newsArray += News(
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    imageUrl = jsonObject.getString("urlToImage"),
                    description = jsonObject.getString("description"),
                    source = jsonObject.getJSONObject("source")
                        .getString("name"),
                        time = finalTime(publishedAt)
                )
            }
            onSuccess(newsArray)
        },
        {
            onFailure(it.message)
        }
    )

    fun getNewsFeed(context: Context) {
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

    suspend fun storeAllNewsItems(items: List<News>) {
        newsFeedDao.saveAllNews(items)
    }

    companion object {
        const val URL = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
    }
}