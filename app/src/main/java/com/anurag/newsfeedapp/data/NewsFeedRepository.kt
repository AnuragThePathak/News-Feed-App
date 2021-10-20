package com.anurag.newsfeedapp.data

import androidx.annotation.WorkerThread
import com.anurag.newsfeedapp.data.db.DiskDataSource
import com.anurag.newsfeedapp.data.network.NetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsFeedRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    @WorkerThread
    suspend fun getNewsFeed(): NewsResponse {
        return try {
            val news = networkDataSource.getNewsFeed()
            diskDataSource.updateCache(news)
            NewsResponse(news = news)
        } catch (ex: Exception) {
            NewsResponse(
                news = diskDataSource.getNews(),
                errorMessage = ex.message
            )
        }
    }
}
