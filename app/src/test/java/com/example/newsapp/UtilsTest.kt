package com.example.newsapp

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for utility functions
 * Tests URL encoding/decoding and extension functions
 */
class UtilsTest {

    // ========== Test 1: String Encode Basic ==========
    @Test
    fun `encode converts string to URL-safe format`() {
        // Given
        val input = "Hello World"

        // When
        val encoded = input.encode()

        // Then
        assertEquals("Hello+World", encoded)
    }

    // ========== Test 2: String Decode Basic ==========
    @Test
    fun `decode converts URL-encoded string back to original`() {
        // Given
        val input = "Hello+World"

        // When
        val decoded = input.decode()

        // Then
        assertEquals("Hello World", decoded)
    }

    // ========== Test 3: Encode Special Characters ==========
    @Test
    fun `encode handles special characters correctly`() {
        // Given
        val input = "test@example.com"

        // When
        val encoded = input.encode()

        // Then
        assertEquals("test%40example.com", encoded)
    }

    // ========== Test 4: Encode and Decode Round Trip ==========
    @Test
    fun `encode and decode round trip preserves original string`() {
        // Given
        val original = "Android & Kotlin Development!"

        // When
        val encoded = original.encode()
        val decoded = encoded.decode()

        // Then
        assertEquals(original, decoded)
    }

    // ========== Test 5: Encode URL with Parameters ==========
    @Test
    fun `encode handles URL with query parameters`() {
        // Given
        val input = "https://example.com?query=test&category=news"

        // When
        val encoded = input.encode()

        // Then
        assertTrue(encoded.contains("%3F")) // ?
        assertTrue(encoded.contains("%3D")) // =
        assertTrue(encoded.contains("%26")) // &
    }

    // ========== Test 6: Decode URL with Parameters ==========
    @Test
    fun `decode handles encoded URL with parameters`() {
        // Given
        val encoded = "https%3A%2F%2Fexample.com%3Fquery%3Dtest"

        // When
        val decoded = encoded.decode()

        // Then
        assertEquals("https://example.com?query=test", decoded)
    }

    // ========== Test 7: Encode Empty String ==========
    @Test
    fun `encode handles empty string`() {
        // Given
        val input = ""

        // When
        val encoded = input.encode()

        // Then
        assertEquals("", encoded)
    }

    // ========== Test 8: Decode Empty String ==========
    @Test
    fun `decode handles empty string`() {
        // Given
        val input = ""

        // When
        val decoded = input.decode()

        // Then
        assertEquals("", decoded)
    }

    // ========== Test 9: Encode Unicode Characters ==========
    @Test
    fun `encode handles unicode characters`() {
        // Given
        val input = "Hello 世界"

        // When
        val encoded = input.encode()

        // Then
        assertTrue(encoded.contains("%"))
        assertNotEquals(input, encoded)
    }

    // ========== Test 10: Decode Unicode Characters ==========
    @Test
    fun `decode handles unicode characters`() {
        // Given
        val input = "Hello 世界"
        val encoded = input.encode()

        // When
        val decoded = encoded.decode()

        // Then
        assertEquals(input, decoded)
    }

    // ========== Test 11: Encode Multiple Spaces ==========
    @Test
    fun `encode handles multiple consecutive spaces`() {
        // Given
        val input = "test    multiple    spaces"

        // When
        val encoded = input.encode()

        // Then
        assertTrue(encoded.contains("+"))
    }

    // ========== Test 12: Encode Special News Characters ==========
    @Test
    fun `encode handles common news article characters`() {
        // Given
        val input = "Breaking News: Tech Giant's Stock Rises 50%!"

        // When
        val encoded = input.encode()
        val decoded = encoded.decode()

        // Then
        assertEquals(input, decoded)
        assertTrue(encoded.contains("%"))
    }

    // ========== Test 13: FAVORITE_CATEGORIES_KEY Constant ==========
    @Test
    fun `FAVORITE_CATEGORIES_KEY has correct value`() {
        // Then
        assertEquals("favorite_categories", FAVORITE_CATEGORIES_KEY.name)
    }

    // ========== Test 14: Encode Slash Characters ==========
    @Test
    fun `encode handles forward slashes`() {
        // Given
        val input = "path/to/resource"

        // When
        val encoded = input.encode()

        // Then
        assertTrue(encoded.contains("%2F"))
    }

    // ========== Test 15: Encode Ampersand ==========
    @Test
    fun `encode handles ampersand character`() {
        // Given
        val input = "News & Updates"

        // When
        val encoded = input.encode()

        // Then
        assertTrue(encoded.contains("%26"))
    }
}
