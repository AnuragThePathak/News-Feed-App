package com.anurag.newsfeedapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anurag.newsfeedapp.data.News

@Database(
    entities = [News::class],
    version = 4,
    exportSchema = false
)
abstract class NewsFeedDatabase : RoomDatabase() {
    abstract fun newsFeedDao(): NewsFeedDao
}