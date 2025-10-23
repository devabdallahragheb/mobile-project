package com.example.newsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for favorite articles
 * Provides CRUD operations for local persistence
 */
@Dao
interface FavoriteArticleDao {
    
    /**
     * Get all favorite articles as a Flow (reactive updates)
     */
    @Query("SELECT * FROM favorite_articles ORDER BY savedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteArticle>>
    
    /**
     * Get a specific favorite article by URL
     */
    @Query("SELECT * FROM favorite_articles WHERE url = :url")
    suspend fun getFavoriteByUrl(url: String): FavoriteArticle?
    
    /**
     * Check if an article is favorited
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_articles WHERE url = :url)")
    fun isFavorite(url: String): Flow<Boolean>
    
    /**
     * Insert a new favorite article
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavoriteArticle)
    
    /**
     * Delete a favorite article
     */
    @Delete
    suspend fun deleteFavorite(article: FavoriteArticle)
    
    /**
     * Delete a favorite by URL
     */
    @Query("DELETE FROM favorite_articles WHERE url = :url")
    suspend fun deleteFavoriteByUrl(url: String)
    
    /**
     * Delete all favorites
     */
    @Query("DELETE FROM favorite_articles")
    suspend fun deleteAllFavorites()
    
    /**
     * Get count of favorite articles
     */
    @Query("SELECT COUNT(*) FROM favorite_articles")
    fun getFavoritesCount(): Flow<Int>
}
