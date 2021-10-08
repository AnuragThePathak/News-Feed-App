package com.anurag.newsfeedapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllNews(news: List<News>)

    @Query("SELECT * FROM news_list")
    suspend fun getAllNewsFeed(): List<News>

}