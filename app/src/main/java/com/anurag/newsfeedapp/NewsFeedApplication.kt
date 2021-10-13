package com.anurag.newsfeedapp

import android.app.Application
import com.anurag.newsfeedapp.data.db.NewsFeedDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsFeedApplication : Application()