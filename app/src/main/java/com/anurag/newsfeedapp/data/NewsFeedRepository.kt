package com.anurag.newsfeedapp.data

import android.annotation.SuppressLint
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.anurag.newsfeedapp.MySingleton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")  //getting current time in specific format
                val currentDate = sdf.format(Date())


                val published = publishedAt.split(Regex("T|-|:|Z")) //filtering published date using regex.
                val current = currentDate.split(Regex("/| |:")) //filtering current date using regex.


                val daysAgo = calDays(published, current)   //calling function calDays, that returns number of days between our published and current date
                val hoursAgo = current[3].toInt() - published[3].toInt()    //calculating hour difference

                var tt : String = if(daysAgo.toInt() != 0) {
                    "$daysAgo day ago"
                }else {
                    "$hoursAgo hour ago"
                } //holds our final time difference, if it's greater than 24 than no. of daysAgo will not be 0, in that case we just need to print time diff


                newsArray += News(
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    imageUrl = jsonObject.getString("urlToImage"),
                    description = jsonObject.getString("description"),
                    source = jsonObject.getJSONObject("source")
                        .getString("name"),
                        time = tt
                )
            }
            onSuccess(newsArray)
        },
        {
            onFailure(it.message)
        }
    )


    //returns difference in days, between two dates, here published and current date.
    private fun calDays(published: List<String>, current: List<String>): Long {
        val d1  = longArrayOf(published[2].toLong(), published[1].toLong(), published[0].toLong())//day month year
        val d2 = longArrayOf(current[0].toLong(), current[1].toLong(), current[2].toLong())

        val monthDays = intArrayOf(31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31 )

        var n1 = (d1[2]*365 + d1[0])

        for(i in 0 until d1[1]) {
            n1 += monthDays[i.toInt()]
        }
        n1 += countLeapYears(d1)

        var n2 = (d2[2]*365 + d2[0])

        for(i in 0 until d2[1]){
            n2 += monthDays[i.toInt()]
        }
        n2 += countLeapYears(d2)
        return n2 - n1


    }

    //checks and adds a day for each no. leap years.
    private fun countLeapYears(d1: LongArray): Long {
       var years = d1[2]
        if(d1[1] <= 2) {
            years -= 1
        }
        return (years/4 - years/100 + years/400)
    }


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