package com.example.newsapp.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.FAVORITE_CATEGORIES_KEY
import com.example.newsapp.dataStore
import com.example.newsapp.model.AIRepository
import com.example.newsapp.model.AISummary
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticleInsights
import com.example.newsapp.model.NewsApiService
import com.example.newsapp.model.SentimentAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject



@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsApiService: NewsApiService,
    val apiKey:String,
    val context: Context,
    val savedStateHandle: SavedStateHandle,
    private val aiRepository: AIRepository

) :ViewModel() {

    private val _articles= mutableStateOf<List<Article>>(emptyList())
    val articles : State<List<Article>> = _articles

    private val _searchArticles = mutableStateOf<List<Article>>(emptyList())
    val searchArticle:State<List<Article>> = _searchArticles

    private val _favoriteArticles = mutableStateOf<List<Article>>(emptyList())
    val favoriteArticles:State<List<Article>> = _favoriteArticles

    val favoriteCategoriesFlow : Flow<List<String>> = context.dataStore.data.map { preferences ->
        preferences[FAVORITE_CATEGORIES_KEY]?.let { Json.decodeFromString(it) } ?: emptyList()
    }

    private val _selectedCategory = mutableStateOf(savedStateHandle.get<String>("selectedCategory") ?: "General")
    val selectedCategory: State<String> = _selectedCategory

    // ========== AI/GenAI Integration States ==========
    private val _aiSummary = mutableStateOf<AISummary?>(null)
    val aiSummary: State<AISummary?> = _aiSummary

    private val _sentimentAnalysis = mutableStateOf<SentimentAnalysis?>(null)
    val sentimentAnalysis: State<SentimentAnalysis?> = _sentimentAnalysis

    private val _articleInsights = mutableStateOf<ArticleInsights?>(null)
    val articleInsights: State<ArticleInsights?> = _articleInsights

    private val _isAILoading = mutableStateOf(false)
    val isAILoading: State<Boolean> = _isAILoading

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        savedStateHandle.set("selectedCategory", category)
        loadHeadlines(category)
    }


    fun loadHeadlines(category:String){
        viewModelScope.launch {
            _articles.value=try {
                newsApiService.getTopHeadlines(
                    category = category.lowercase(),
                    apiKey = apiKey
                ).articles
            }catch (e:Exception) {
                println("Error loading articles : ${e.message}")
                emptyList()
            }
        }
    }

    fun searchNews(query:String){
        viewModelScope.launch {
            _searchArticles.value=if (query.isNotEmpty()){
                try {
                    newsApiService.searchNews(query,apiKey).articles
                }catch (e:Exception){
                    println("Error loading search articles : ${e.message}")
                    emptyList()
                }
            }else{
                emptyList()
            }
        }
    }

    fun loadFavoriteArticles(){
        viewModelScope.launch {
            val allArticles = mutableListOf<Article>()
            favoriteCategoriesFlow.collect{categories ->
                categories.forEach { category ->
                    val response = try {
                        newsApiService.getTopHeadlines(
                            category = category.lowercase(),
                            apiKey = apiKey
                        ).articles
                    }catch (e:Exception){
                        println("Error loading favorite articles : ${e.message}")
                        emptyList()
                    }
                    allArticles.addAll(response)
                }
                _favoriteArticles.value=allArticles.distinctBy { it.content }
            }
        }
    }

   fun updateFavoriteCategories(category: String,isSelected:Boolean){
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                val currentList = favoriteCategoriesFlow.first()
                val updatedList = if (isSelected){
                    currentList - category
                }else{
                    currentList + category
                }
                preferences[FAVORITE_CATEGORIES_KEY] = Json.encodeToString(updatedList)
            }
        }
    }

    // ========== Generative AI Functions ==========
    
    /**
     * Generate AI summary for an article using OpenAI GPT
     * Example usage: Summarize long articles into concise 2-3 sentences
     */
    fun generateAISummary(article: Article) {
        viewModelScope.launch {
            _isAILoading.value = true
            try {
                val content = "${article.title}. ${article.description ?: ""} ${article.content ?: ""}"
                val summary = aiRepository.summarizeArticle(content)
                _aiSummary.value = summary
            } catch (e: Exception) {
                _aiSummary.value = AISummary(
                    summary = "",
                    success = false,
                    error = "Failed to generate summary: ${e.message}"
                )
            } finally {
                _isAILoading.value = false
            }
        }
    }

    /**
     * Analyze sentiment of an article using OpenAI GPT
     * Example usage: Determine if news is positive, negative, or neutral
     */
    fun analyzeSentiment(article: Article) {
        viewModelScope.launch {
            _isAILoading.value = true
            try {
                val content = "${article.title}. ${article.description ?: ""} ${article.content ?: ""}"
                val analysis = aiRepository.analyzeSentiment(content)
                _sentimentAnalysis.value = analysis
            } catch (e: Exception) {
                _sentimentAnalysis.value = SentimentAnalysis(
                    sentiment = "Unknown",
                    confidence = "Low",
                    explanation = "Failed to analyze: ${e.message}",
                    success = false
                )
            } finally {
                _isAILoading.value = false
            }
        }
    }

    /**
     * Generate key insights from an article using OpenAI GPT
     * Example usage: Extract 3-5 actionable takeaways from news articles
     */
    fun generateInsights(article: Article) {
        viewModelScope.launch {
            _isAILoading.value = true
            try {
                val content = "${article.title}. ${article.description ?: ""} ${article.content ?: ""}"
                val insights = aiRepository.generateInsights(content)
                _articleInsights.value = insights
            } catch (e: Exception) {
                _articleInsights.value = ArticleInsights(
                    insights = emptyList(),
                    success = false,
                    error = "Failed to generate insights: ${e.message}"
                )
            } finally {
                _isAILoading.value = false
            }
        }
    }

    /**
     * Generate all AI features at once for comprehensive article analysis
     */
    fun generateAllAIFeatures(article: Article) {
        generateAISummary(article)
        analyzeSentiment(article)
        generateInsights(article)
    }

    /**
     * Clear AI analysis results
     */
    fun clearAIAnalysis() {
        _aiSummary.value = null
        _sentimentAnalysis.value = null
        _articleInsights.value = null
    }
}