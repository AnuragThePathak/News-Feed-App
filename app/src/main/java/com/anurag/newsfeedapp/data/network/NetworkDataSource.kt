package com.anurag.newsfeedapp.data.network

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.data.News
import com.anurag.newsfeedapp.data.finalTime
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class NetworkDataSource @Inject constructor(
    private val networkClient: NetworkClient
) {

    suspend fun getNewsFeed(): List<News> {
        return fetchNews()
    }

    private suspend fun fetchNews(): List<News> = suspendCoroutine {
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
        networkClient.addToRequestQueue(request)
    }

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