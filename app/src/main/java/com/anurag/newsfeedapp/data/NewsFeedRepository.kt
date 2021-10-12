package com.anurag.newsfeedapp.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.MySingleton
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

class NewsFeedRepository(
    private val newsFeedDao: NewsFeedDao,
) {

    suspend fun getNewsFeed(context: Context): NewsResponse {
        return try {
            val news = fetchNews(context)
            NewsResponse(news = news)
                .also {
                    storeAllNewsItems(it.news)
                }
        } catch (ex: Exception) {
            val cachedNews = getCachedNews()
            NewsResponse(
                news = cachedNews,
                errorMessage = ex.message
            )
        }
    }

    private suspend fun fetchNews(context: Context): List<News> = suspendCoroutine {
        val request = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                val news = parseResponse(response)
                it.resumeWith(Result.success(news))
            }
        ) { error ->
            it.resumeWith(Result.failure(error))
        }
        MySingleton.getInstance(context).addToRequestQueue(request)
    }

    private suspend fun storeAllNewsItems(items: List<News>) {
        newsFeedDao.saveAllNews(items)
    }

    private suspend fun getCachedNews(): List<News> = newsFeedDao.getAllNewsFeed()

    private fun parseResponse(response: JSONObject): List<News> {
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
        return newsArray
    }

    companion object {
        const val URL = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
    }
}