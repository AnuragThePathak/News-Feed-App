package com.anurag.newsfeedapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_list")
data class News(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "source") val source: String
)
