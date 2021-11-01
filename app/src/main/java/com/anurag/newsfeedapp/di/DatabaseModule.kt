package com.anurag.newsfeedapp.di

import android.content.Context
import com.anurag.newsfeedapp.data.db.NewsFeedDao
import com.anurag.newsfeedapp.data.db.NewsFeedDatabase
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
        return NewsFeedDatabase.getDatabase(context)
    }

    @Provides
    fun provideNewsFeedDao(database: NewsFeedDatabase): NewsFeedDao {
        return database.newsFeedDao()
    }
}