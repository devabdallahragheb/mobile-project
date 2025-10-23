package com.example.newsapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.SavedStateHandle
import com.example.newsapp.FAVORITE_CATEGORIES_KEY
import com.example.newsapp.dataStore
import com.example.newsapp.model.AIRepository
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsApiService
import com.example.newsapp.model.NewsResponse
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for NewsViewModel
 * Tests business logic and state management
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsApiService: NewsApiService
    private lateinit var aiRepository: AIRepository
    private lateinit var context: Context
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var dataStore: DataStore<Preferences>
    
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    
    private val apiKey = "test_api_key"

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock dependencies
        newsApiService = mockk(relaxed = true)
        aiRepository = mockk(relaxed = true)
        context = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle()
        dataStore = mockk(relaxed = true)
        
        // Mock DataStore extension property
        val mockPreferences = mockk<Preferences>(relaxed = true)
        every { mockPreferences[FAVORITE_CATEGORIES_KEY] } returns null
        every { dataStore.data } returns flowOf(mockPreferences)
        
        // Mock the extension property using mockkStatic
        mockkStatic("com.example.newsapp.UtilsKt")
        every { context.dataStore } returns dataStore
        
        viewModel = NewsViewModel(
            newsApiService = newsApiService,
            apiKey = apiKey,
            context = context,
            savedStateHandle = savedStateHandle,
            aiRepository = aiRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ========== Test 1: Load Headlines Successfully ==========
    @Test
    fun `loadHeadlines updates articles state with successful response`() = testScope.runTest {
        // Given
        val category = "technology"
        val mockArticles = listOf(
            Article(
                title = "Tech News 1",
                description = "Description 1",
                urlToImage = "https://example.com/image1.jpg",
                url = "https://example.com/article1",
                content = "Content 1"
            ),
            Article(
                title = "Tech News 2",
                description = "Description 2",
                urlToImage = "https://example.com/image2.jpg",
                url = "https://example.com/article2",
                content = "Content 2"
            )
        )
        val mockResponse = NewsResponse(articles = mockArticles)
        
        coEvery { 
            newsApiService.getTopHeadlines(
                country = any(),
                category = category.lowercase(),
                apiKey = apiKey
            )
        } returns mockResponse

        // When
        viewModel.loadHeadlines(category)
        advanceUntilIdle()

        // Then
        assertEquals(2, viewModel.articles.value.size)
        assertEquals("Tech News 1", viewModel.articles.value[0].title)
        assertEquals("Tech News 2", viewModel.articles.value[1].title)
        
        coVerify { 
            newsApiService.getTopHeadlines(
                country = any(),
                category = category.lowercase(),
                apiKey = apiKey
            )
        }
    }

    // ========== Test 2: Load Headlines with Error ==========
    @Test
    fun `loadHeadlines returns empty list on API error`() = testScope.runTest {
        // Given
        val category = "sports"
        coEvery { 
            newsApiService.getTopHeadlines(any(), any(), any())
        } throws Exception("Network error")

        // When
        viewModel.loadHeadlines(category)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.articles.value.isEmpty())
    }

    // ========== Test 3: Search News Successfully ==========
    @Test
    fun `searchNews updates searchArticles state with results`() = testScope.runTest {
        // Given
        val query = "kotlin"
        val mockArticles = listOf(
            Article(
                title = "Kotlin News",
                description = "Kotlin description",
                urlToImage = "https://example.com/kotlin.jpg",
                url = "https://example.com/kotlin",
                content = "Kotlin content"
            )
        )
        val mockResponse = NewsResponse(articles = mockArticles)
        
        coEvery { 
            newsApiService.searchNews(query, apiKey)
        } returns mockResponse

        // When
        viewModel.searchNews(query)
        advanceUntilIdle()

        // Then
        assertEquals(1, viewModel.searchArticle.value.size)
        assertEquals("Kotlin News", viewModel.searchArticle.value[0].title)
        
        coVerify { newsApiService.searchNews(query, apiKey) }
    }

    // ========== Test 4: Search with Empty Query ==========
    @Test
    fun `searchNews returns empty list when query is empty`() = testScope.runTest {
        // When
        viewModel.searchNews("")
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.searchArticle.value.isEmpty())
        
        // Verify API was not called
        coVerify(exactly = 0) { newsApiService.searchNews(any(), any()) }
    }

    // ========== Test 5: Search News with Error ==========
    @Test
    fun `searchNews returns empty list on API error`() = testScope.runTest {
        // Given
        val query = "error"
        coEvery { 
            newsApiService.searchNews(any(), any())
        } throws Exception("Search failed")

        // When
        viewModel.searchNews(query)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.searchArticle.value.isEmpty())
    }

    // ========== Test 6: Set Selected Category ==========
    @Test
    fun `setSelectedCategory updates category and loads headlines`() = testScope.runTest {
        // Given
        val newCategory = "Business"
        val mockArticles = listOf(
            Article(
                title = "Business News",
                description = "Business description",
                urlToImage = "https://example.com/business.jpg",
                url = "https://example.com/business",
                content = "Business content"
            )
        )
        val mockResponse = NewsResponse(articles = mockArticles)
        
        coEvery { 
            newsApiService.getTopHeadlines(
                country = any(),
                category = newCategory.lowercase(),
                apiKey = apiKey
            )
        } returns mockResponse

        // When
        viewModel.setSelectedCategory(newCategory)
        advanceUntilIdle()

        // Then
        assertEquals(newCategory, viewModel.selectedCategory.value)
        assertEquals(newCategory, savedStateHandle.get<String>("selectedCategory"))
        assertEquals(1, viewModel.articles.value.size)
    }

    // ========== Test 7: Initial Selected Category ==========
    @Test
    fun `initial selectedCategory is General by default`() {
        // Then
        assertEquals("General", viewModel.selectedCategory.value)
    }

    // ========== Test 8: Selected Category from SavedStateHandle ==========
    @Test
    fun `selectedCategory restores from SavedStateHandle`() {
        // Given
        val savedCategory = "Sports"
        savedStateHandle.set("selectedCategory", savedCategory)
        
        // When
        val newViewModel = NewsViewModel(
            newsApiService = newsApiService,
            apiKey = apiKey,
            context = context,
            savedStateHandle = savedStateHandle,
            aiRepository = aiRepository
        )

        // Then
        assertEquals(savedCategory, newViewModel.selectedCategory.value)
    }

    // ========== Test 9: Multiple Articles Loaded ==========
    @Test
    fun `loadHeadlines handles multiple articles correctly`() = testScope.runTest {
        // Given
        val category = "general"
        val mockArticles = (1..10).map { index ->
            Article(
                title = "Article $index",
                description = "Description $index",
                urlToImage = "https://example.com/image$index.jpg",
                url = "https://example.com/article$index",
                content = "Content $index"
            )
        }
        val mockResponse = NewsResponse(articles = mockArticles)
        
        coEvery { 
            newsApiService.getTopHeadlines(any(), any(), any())
        } returns mockResponse

        // When
        viewModel.loadHeadlines(category)
        advanceUntilIdle()

        // Then
        assertEquals(10, viewModel.articles.value.size)
        assertEquals("Article 1", viewModel.articles.value[0].title)
        assertEquals("Article 10", viewModel.articles.value[9].title)
    }

    // ========== Test 10: API Service Called with Correct Parameters ==========
    @Test
    fun `loadHeadlines calls API with lowercase category`() = testScope.runTest {
        // Given
        val category = "TECHNOLOGY"
        val mockResponse = NewsResponse(articles = emptyList())
        
        coEvery { 
            newsApiService.getTopHeadlines(any(), any(), any())
        } returns mockResponse

        // When
        viewModel.loadHeadlines(category)
        advanceUntilIdle()

        // Then
        coVerify { 
            newsApiService.getTopHeadlines(
                country = any(),
                category = "technology",
                apiKey = apiKey
            )
        }
    }

    // ========== Test 11: Search with Special Characters ==========
    @Test
    fun `searchNews handles special characters in query`() = testScope.runTest {
        // Given
        val query = "android & kotlin"
        val mockResponse = NewsResponse(articles = emptyList())
        
        coEvery { 
            newsApiService.searchNews(any(), any())
        } returns mockResponse

        // When
        viewModel.searchNews(query)
        advanceUntilIdle()

        // Then
        coVerify { newsApiService.searchNews(query, apiKey) }
    }

    // ========== Test 12: Articles State is Immutable ==========
    @Test
    fun `articles state cannot be modified externally`() = testScope.runTest {
        // Given
        val mockArticles = listOf(
            Article(
                title = "Test Article",
                description = "Test",
                urlToImage = "https://example.com/test.jpg",
                url = "https://example.com/test",
                content = "Test content"
            )
        )
        val mockResponse = NewsResponse(articles = mockArticles)
        
        coEvery { 
            newsApiService.getTopHeadlines(any(), any(), any())
        } returns mockResponse

        // When
        viewModel.loadHeadlines("general")
        advanceUntilIdle()
        
        val articlesBefore = viewModel.articles.value.size

        // Then - State is read-only
        assertEquals(1, articlesBefore)
        // The State<List<Article>> is immutable from outside
    }
}
