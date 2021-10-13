package com.anurag.newsfeedapp.data.db

import com.anurag.newsfeedapp.data.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiskDataSource @Inject constructor(
    private val newsFeedDao: NewsFeedDao
) {

    suspend fun updateCache(news: List<News>) {
        newsFeedDao.clearNews()
        newsFeedDao.saveAllNews(news)
    }

    suspend fun getNews(): List<News> {
        return newsFeedDao.getAllNewsFeed()
    }
}