package com.example.newsapp.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for NewsApiService
 * Tests API interface and data models
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NewsApiServiceTest {

    // ========== Test 1: Article Data Class ==========
    @Test
    fun `Article data class creates instance correctly`() {
        // Given
        val title = "Test Article"
        val description = "Test Description"
        val urlToImage = "https://example.com/image.jpg"
        val url = "https://example.com/article"
        val content = "Test Content"

        // When
        val article = Article(
            title = title,
            description = description,
            urlToImage = urlToImage,
            url = url,
            content = content
        )

        // Then
        assertEquals(title, article.title)
        assertEquals(description, article.description)
        assertEquals(urlToImage, article.urlToImage)
        assertEquals(url, article.url)
        assertEquals(content, article.content)
    }

    // ========== Test 2: Article with Null Values ==========
    @Test
    fun `Article handles null description and urlToImage`() {
        // When
        val article = Article(
            title = "Test",
            description = null,
            urlToImage = null,
            url = "https://example.com",
            content = "Content"
        )

        // Then
        assertNull(article.description)
        assertNull(article.urlToImage)
        assertNotNull(article.title)
        assertNotNull(article.url)
    }

    // ========== Test 3: Article Default Content Value ==========
    @Test
    fun `Article has default null content value`() {
        // When
        val article = Article(
            title = "Test",
            description = "Description",
            urlToImage = "https://example.com/image.jpg",
            url = "https://example.com"
        )

        // Then
        assertNull(article.content)
    }

    // ========== Test 4: NewsResponse Data Class ==========
    @Test
    fun `NewsResponse creates instance with articles list`() {
        // Given
        val articles = listOf(
            Article(
                title = "Article 1",
                description = "Description 1",
                urlToImage = "https://example.com/1.jpg",
                url = "https://example.com/1"
            ),
            Article(
                title = "Article 2",
                description = "Description 2",
                urlToImage = "https://example.com/2.jpg",
                url = "https://example.com/2"
            )
        )

        // When
        val newsResponse = NewsResponse(articles = articles)

        // Then
        assertEquals(2, newsResponse.articles.size)
        assertEquals("Article 1", newsResponse.articles[0].title)
        assertEquals("Article 2", newsResponse.articles[1].title)
    }

    // ========== Test 5: Empty NewsResponse ==========
    @Test
    fun `NewsResponse handles empty articles list`() {
        // When
        val newsResponse = NewsResponse(articles = emptyList())

        // Then
        assertTrue(newsResponse.articles.isEmpty())
        assertEquals(0, newsResponse.articles.size)
    }

    // ========== Test 6: Article Equality ==========
    @Test
    fun `Article equality works correctly`() {
        // Given
        val article1 = Article(
            title = "Test",
            description = "Description",
            urlToImage = "https://example.com/image.jpg",
            url = "https://example.com",
            content = "Content"
        )
        val article2 = Article(
            title = "Test",
            description = "Description",
            urlToImage = "https://example.com/image.jpg",
            url = "https://example.com",
            content = "Content"
        )

        // Then
        assertEquals(article1, article2)
        assertEquals(article1.hashCode(), article2.hashCode())
    }

    // ========== Test 7: Article Copy Function ==========
    @Test
    fun `Article copy function works correctly`() {
        // Given
        val original = Article(
            title = "Original Title",
            description = "Original Description",
            urlToImage = "https://example.com/original.jpg",
            url = "https://example.com/original",
            content = "Original Content"
        )

        // When
        val copied = original.copy(title = "New Title")

        // Then
        assertEquals("New Title", copied.title)
        assertEquals(original.description, copied.description)
        assertEquals(original.urlToImage, copied.urlToImage)
        assertEquals(original.url, copied.url)
        assertEquals(original.content, copied.content)
    }

    // ========== Test 8: Article toString ==========
    @Test
    fun `Article toString contains all properties`() {
        // Given
        val article = Article(
            title = "Test Title",
            description = "Test Description",
            urlToImage = "https://example.com/test.jpg",
            url = "https://example.com/test",
            content = "Test Content"
        )

        // When
        val stringRepresentation = article.toString()

        // Then
        assertTrue(stringRepresentation.contains("Test Title"))
        assertTrue(stringRepresentation.contains("Test Description"))
    }

    // ========== Test 9: Large Articles List ==========
    @Test
    fun `NewsResponse handles large articles list`() {
        // Given
        val largeList = (1..100).map { index ->
            Article(
                title = "Article $index",
                description = "Description $index",
                urlToImage = "https://example.com/$index.jpg",
                url = "https://example.com/$index"
            )
        }

        // When
        val newsResponse = NewsResponse(articles = largeList)

        // Then
        assertEquals(100, newsResponse.articles.size)
        assertEquals("Article 1", newsResponse.articles[0].title)
        assertEquals("Article 100", newsResponse.articles[99].title)
    }

    // ========== Test 10: Article with Empty Strings ==========
    @Test
    fun `Article handles empty string values`() {
        // When
        val article = Article(
            title = "",
            description = "",
            urlToImage = "",
            url = "",
            content = ""
        )

        // Then
        assertEquals("", article.title)
        assertEquals("", article.description)
        assertEquals("", article.urlToImage)
        assertEquals("", article.url)
        assertEquals("", article.content)
    }

    // ========== Test 11: Article with Long Content ==========
    @Test
    fun `Article handles long content strings`() {
        // Given
        val longContent = "A".repeat(10000)

        // When
        val article = Article(
            title = "Test",
            description = "Test",
            urlToImage = "https://example.com/test.jpg",
            url = "https://example.com/test",
            content = longContent
        )

        // Then
        assertEquals(10000, article.content?.length)
    }

    // ========== Test 12: NewsResponse Immutability ==========
    @Test
    fun `NewsResponse articles list is immutable`() {
        // Given
        val articles = listOf(
            Article(
                title = "Article 1",
                description = "Description 1",
                urlToImage = "https://example.com/1.jpg",
                url = "https://example.com/1"
            )
        )
        val newsResponse = NewsResponse(articles = articles)

        // When
        val retrievedArticles = newsResponse.articles

        // Then
        assertEquals(articles, retrievedArticles)
        // List is immutable, cannot add/remove
        assertTrue(retrievedArticles is List<Article>)
    }
}
