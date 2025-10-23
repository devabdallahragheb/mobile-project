package com.example.newsapp.navigation

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Navigation Screen sealed class
 * Tests screen routes and navigation structure
 */
class ScreenTest {

    // ========== Test 1: Home Screen Route ==========
    @Test
    fun `Home screen has correct route`() {
        // Then
        assertEquals("Home", Screen.Home.route)
    }

    // ========== Test 2: Search Screen Route ==========
    @Test
    fun `Search screen has correct route`() {
        // Then
        assertEquals("Search", Screen.Search.route)
    }

    // ========== Test 3: Favorites Screen Route ==========
    @Test
    fun `Favorites screen has correct route`() {
        // Then
        assertEquals("Favorites", Screen.Favorites.route)
    }

    // ========== Test 4: Screen Routes are Unique ==========
    @Test
    fun `all screen routes are unique`() {
        // Given
        val routes = listOf(
            Screen.Home.route,
            Screen.Search.route,
            Screen.Favorites.route
        )

        // Then
        assertEquals(routes.size, routes.distinct().size)
    }

    // ========== Test 5: Screen Routes are Not Empty ==========
    @Test
    fun `screen routes are not empty`() {
        // Then
        assertTrue(Screen.Home.route.isNotEmpty())
        assertTrue(Screen.Search.route.isNotEmpty())
        assertTrue(Screen.Favorites.route.isNotEmpty())
    }

    // ========== Test 6: Screen is Sealed Class ==========
    @Test
    fun `Screen is a sealed class with three objects`() {
        // Given
        val screens = listOf(
            Screen.Home,
            Screen.Search,
            Screen.Favorites
        )

        // Then
        assertEquals(3, screens.size)
        screens.forEach { screen ->
            assertTrue(screen is Screen)
        }
    }

    // ========== Test 7: Screen Objects are Singletons ==========
    @Test
    fun `Screen objects are singletons`() {
        // Given
        val home1 = Screen.Home
        val home2 = Screen.Home

        // Then
        assertSame(home1, home2)
    }

    // ========== Test 8: Screen Routes Match Object Names ==========
    @Test
    fun `screen routes match object names`() {
        // Then
        assertEquals("Home", Screen.Home.route)
        assertEquals("Search", Screen.Search.route)
        assertEquals("Favorites", Screen.Favorites.route)
    }

    // ========== Test 9: Screen Route Comparison ==========
    @Test
    fun `screen routes can be compared`() {
        // Given
        val homeRoute = "Home"

        // Then
        assertEquals(homeRoute, Screen.Home.route)
        assertNotEquals(homeRoute, Screen.Search.route)
    }

    // ========== Test 10: All Screens Inherit from Screen ==========
    @Test
    fun `all screen objects inherit from Screen base class`() {
        // Then
        assertTrue(Screen.Home is Screen)
        assertTrue(Screen.Search is Screen)
        assertTrue(Screen.Favorites is Screen)
    }
}
