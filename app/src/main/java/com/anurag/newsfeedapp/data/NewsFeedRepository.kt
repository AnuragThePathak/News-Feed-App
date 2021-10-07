package com.anurag.newsfeedapp.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.MySingleton

class NewsFeedRepository(
    onSuccess: (ArrayList<News>) -> Unit,
    onFailure: (String?) -> Unit
) {
    private val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, URL, null,
        { response ->
            val jsonArray = response.getJSONArray("articles")
            val newsArray = ArrayList<News>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                newsArray += News(
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    imageUrl = jsonObject.getString("urlToImage"),
                    description = jsonObject.getString("description"),
                    source = jsonObject.getJSONObject("source")
                        .getString("name")
                )
            }
            onSuccess(newsArray)
        },
        {
            onFailure(it.message)
        }
    )

    private var databaseInstance: NewsFeedDatabase? = null

    fun connectDatabase(database: NewsFeedDatabase) {
        databaseInstance = database
    }

    fun getNewsFeed(context: Context) {
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

    suspend fun storeAllNewsItems(items: List<News>) {
        databaseInstance?.newsFeedDao()?.saveAllNews(items)
    }

    companion object {
        const val URL = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
    }
}