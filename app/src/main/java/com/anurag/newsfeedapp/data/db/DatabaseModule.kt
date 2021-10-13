package com.anurag.newsfeedapp.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsFeedDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsFeedDatabase::class.java,
            "news_list"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNewsFeedDao(database: NewsFeedDatabase): NewsFeedDao {
        return database.newsFeedDao()
    }
}