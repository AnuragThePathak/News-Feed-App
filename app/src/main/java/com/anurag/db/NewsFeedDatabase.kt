package com.anurag.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anurag.newsfeedapp.News

@Database(
    entities = [News::class],
    version = 1,
    exportSchema = false
)
abstract class NewsFeedDatabase : RoomDatabase() {

    abstract fun newsFeedDao(): NewsFeedDao

    companion object {
        @Volatile
        private var INSTANCE: NewsFeedDatabase? = null

        fun getDatabase(context: Context): NewsFeedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsFeedDatabase::class.java,
                    "news_list"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}