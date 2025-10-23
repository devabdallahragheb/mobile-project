package com.example.newsapp.data

import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing favorite articles
 * Provides a clean API for CRUD operations
 */
@Singleton
class FavoriteRepository @Inject constructor(
    private val dao: FavoriteArticleDao
) {
    
    /**
     * Get all favorite articles as a Flow
     */
    fun getAllFavorites(): Flow<List<FavoriteArticle>> = dao.getAllFavorites()
    
    /**
     * Check if an article is favorited
     */
    fun isFavorite(url: String): Flow<Boolean> = dao.isFavorite(url)
    
    /**
     * Get favorites count
     */
    fun getFavoritesCount(): Flow<Int> = dao.getFavoritesCount()
    
    /**
     * Add an article to favorites
     */
    suspend fun addToFavorites(article: Article) {
        val favoriteArticle = FavoriteArticle(
            url = article.url,
            title = article.title,
            description = article.description,
            urlToImage = article.urlToImage,
            content = article.content
        )
        dao.insertFavorite(favoriteArticle)
    }
    
    /**
     * Remove an article from favorites
     */
    suspend fun removeFromFavorites(url: String) {
        dao.deleteFavoriteByUrl(url)
    }
    
    /**
     * Toggle favorite status
     */
    suspend fun toggleFavorite(article: Article) {
        val existing = dao.getFavoriteByUrl(article.url)
        if (existing != null) {
            dao.deleteFavoriteByUrl(article.url)
        } else {
            addToFavorites(article)
        }
    }
    
    /**
     * Clear all favorites
     */
    suspend fun clearAllFavorites() {
        dao.deleteAllFavorites()
    }
    
    /**
     * Convert FavoriteArticle to Article
     */
    fun FavoriteArticle.toArticle(): Article {
        return Article(
            title = this.title,
            description = this.description,
            url = this.url,
            urlToImage = this.urlToImage,
            content = this.content
        )
    }
}
