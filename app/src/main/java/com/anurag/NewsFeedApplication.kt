package com.anurag

import android.app.Application
import com.anurag.db.NewsFeedDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NewsFeedApplication: Application() {
    val database by lazy { NewsFeedDatabase.getDatabase(this) }
    val applicationScope = CoroutineScope(SupervisorJob())
}