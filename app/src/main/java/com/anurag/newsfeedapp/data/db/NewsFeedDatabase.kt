package com.anurag.newsfeedapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anurag.newsfeedapp.data.News

@Database(
    entities = [News::class],
    version = 4,
    exportSchema = false
)
abstract class NewsFeedDatabase : RoomDatabase() {
    abstract fun newsFeedDao(): NewsFeedDao

    companion object {
        @Volatile
        private var INSTANCE: NewsFeedDatabase? = null

        fun getDatabase(context: Context): NewsFeedDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsFeedDatabase::class.java,
                    "news_list"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}