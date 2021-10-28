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
    suspend fun getNewsFeed(category: String): NewsResponse {
        val categoryId = category.lowercase()
        return try {
            val news = networkDataSource.getNewsFeed(categoryId)
            if (categoryId == DEFAULT_CATEGORY) {
                diskDataSource.updateCache(news)
            }
            NewsResponse(news = news)
        } catch (ex: Exception) {
            NewsResponse(
                news = if (categoryId == DEFAULT_CATEGORY) diskDataSource.getNews() else emptyList(),
                errorMessage = ex.message
            )
        }
    }

    companion object {
        const val DEFAULT_CATEGORY = "general"
    }
}
