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

    suspend fun getNewsFeed(category: String): List<News> {
        return fetchNews(category)
    }

    private suspend fun fetchNews(category: String): List<News> = suspendCoroutine {
        val url = "$CATEGORY_END_POINT/${category.lowercase()}/in.json"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,

            { response ->
                val news = parseResponse(response)
                it.resumeWith(Result.success(news))
            },

            { error ->
                it.resumeWith(Result.failure(error))
            })

        networkClient.addToRequestQueue(request)
    }

    private fun parseResponse(response: JSONObject): List<News> {
        val jsonArray = response.getJSONArray("articles")
        val newsArray = ArrayList<News>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val publishedAt =
                jsonObject.getString("publishedAt")      //fetching publishedAt from the JSON file

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
        const val CATEGORY_END_POINT = "https://saurav.tech/NewsAPI/top-headlines/category"
    }

}