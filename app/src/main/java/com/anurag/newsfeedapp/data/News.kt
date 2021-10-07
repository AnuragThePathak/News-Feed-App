package com.anurag.newsfeedapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_list")
data class News(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val url: String,
    val imageUrl: String,
    val description: String,
    val source: String
)
