package com.anurag.newsfeedapp

import android.app.Application
import com.anurag.newsfeedapp.data.NewsFeedDatabase

class NewsFeedApplication: Application() {
    val database by lazy { NewsFeedDatabase.getDatabase(this) }
}