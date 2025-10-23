package com.example.newsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room Database for NewsApp
 * Stores favorite articles locally for offline access
 */
@Database(
    entities = [FavoriteArticle::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun favoriteArticleDao(): FavoriteArticleDao
}
