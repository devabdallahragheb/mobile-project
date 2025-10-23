package com.example.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity for storing favorite articles locally
 * Provides offline access to saved articles
 */
@Entity(tableName = "favorite_articles")
data class FavoriteArticle(
    @PrimaryKey
    val url: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val savedAt: Long = System.currentTimeMillis()
)
