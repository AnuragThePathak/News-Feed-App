package com.anurag.newsfeedapp

import android.app.Application
import com.anurag.newsfeedapp.data.NewsFeedDatabase

class NewsFeedApplication : Application() {
    val dataBase by lazy { NewsFeedDatabase.getDatabase(this) }
}