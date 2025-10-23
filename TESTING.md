# Testing Documentation

## Overview

This document describes the comprehensive test suite for the NewsApp project. All tests follow best practices and cover critical functionality.

## Test Coverage

### 1. **NewsViewModelTest** (12 Tests)
**Location**: `app/src/test/java/com/example/newsapp/viewmodel/NewsViewModelTest.kt`

Tests the core business logic and state management of the NewsViewModel:

| Test # | Test Name | Description |
|--------|-----------|-------------|
| 1 | `loadHeadlines updates articles state with successful response` | Verifies articles are loaded correctly from API |
| 2 | `loadHeadlines returns empty list on API error` | Tests error handling when API fails |
| 3 | `searchNews updates searchArticles state with results` | Tests search functionality |
| 4 | `searchNews returns empty list when query is empty` | Validates empty query handling |
| 5 | `searchNews returns empty list on API error` | Tests search error handling |
| 6 | `setSelectedCategory updates category and loads headlines` | Tests category selection |
| 7 | `initial selectedCategory is General by default` | Validates default state |
| 8 | `selectedCategory restores from SavedStateHandle` | Tests state persistence |
| 9 | `loadHeadlines handles multiple articles correctly` | Tests list handling |
| 10 | `loadHeadlines calls API with lowercase category` | Validates API parameter formatting |
| 11 | `searchNews handles special characters in query` | Tests edge cases |
| 12 | `articles state cannot be modified externally` | Tests immutability |

### 2. **NewsApiServiceTest** (12 Tests)
**Location**: `app/src/test/java/com/example/newsapp/model/NewsApiServiceTest.kt`

Tests data models and API interface:

| Test # | Test Name | Description |
|--------|-----------|-------------|
| 1 | `Article data class creates instance correctly` | Tests Article model creation |
| 2 | `Article handles null description and urlToImage` | Tests nullable fields |
| 3 | `Article has default null content value` | Tests default parameters |
| 4 | `NewsResponse creates instance with articles list` | Tests response model |
| 5 | `NewsResponse handles empty articles list` | Tests empty state |
| 6 | `Article equality works correctly` | Tests data class equality |
| 7 | `Article copy function works correctly` | Tests copy functionality |
| 8 | `Article toString contains all properties` | Tests string representation |
| 9 | `NewsResponse handles large articles list` | Tests scalability |
| 10 | `Article handles empty string values` | Tests edge cases |
| 11 | `Article handles long content strings` | Tests large data |
| 12 | `NewsResponse articles list is immutable` | Tests immutability |

### 3. **UtilsTest** (15 Tests)
**Location**: `app/src/test/java/com/example/newsapp/UtilsTest.kt`

Tests utility functions for URL encoding/decoding:

| Test # | Test Name | Description |
|--------|-----------|-------------|
| 1 | `encode converts string to URL-safe format` | Tests basic encoding |
| 2 | `decode converts URL-encoded string back to original` | Tests basic decoding |
| 3 | `encode handles special characters correctly` | Tests special chars |
| 4 | `encode and decode round trip preserves original string` | Tests reversibility |
| 5 | `encode handles URL with query parameters` | Tests complex URLs |
| 6 | `decode handles encoded URL with parameters` | Tests URL decoding |
| 7 | `encode handles empty string` | Tests edge case |
| 8 | `decode handles empty string` | Tests edge case |
| 9 | `encode handles unicode characters` | Tests internationalization |
| 10 | `decode handles unicode characters` | Tests unicode support |
| 11 | `encode handles multiple consecutive spaces` | Tests whitespace |
| 12 | `encode handles common news article characters` | Tests real-world data |
| 13 | `FAVORITE_CATEGORIES_KEY has correct value` | Tests constant |
| 14 | `encode handles forward slashes` | Tests path encoding |
| 15 | `encode handles ampersand character` | Tests special chars |

### 4. **ScreenTest** (10 Tests)
**Location**: `app/src/test/java/com/example/newsapp/navigation/ScreenTest.kt`

Tests navigation structure:

| Test # | Test Name | Description |
|--------|-----------|-------------|
| 1 | `Home screen has correct route` | Tests Home route |
| 2 | `Search screen has correct route` | Tests Search route |
| 3 | `Favorites screen has correct route` | Tests Favorites route |
| 4 | `all screen routes are unique` | Tests uniqueness |
| 5 | `screen routes are not empty` | Tests validity |
| 6 | `Screen is a sealed class with three objects` | Tests structure |
| 7 | `Screen objects are singletons` | Tests singleton pattern |
| 8 | `screen routes match object names` | Tests consistency |
| 9 | `screen routes can be compared` | Tests comparison |
| 10 | `all screen objects inherit from Screen base class` | Tests inheritance |

## Total Test Count

- **NewsViewModelTest**: 12 tests
- **NewsApiServiceTest**: 12 tests
- **UtilsTest**: 15 tests
- **ScreenTest**: 10 tests
- **Total**: **49 unit tests**

## Running Tests

### Option 1: Android Studio (Recommended)

1. **Run All Tests**:
   - Right-click on `app/src/test/java` folder
   - Select "Run 'Tests in 'java''"

2. **Run Specific Test Class**:
   - Open the test file (e.g., `NewsViewModelTest.kt`)
   - Click the green play button next to the class name
   - Or right-click → "Run 'NewsViewModelTest'"

3. **Run Single Test**:
   - Click the green play button next to any `@Test` function
   - Or right-click → "Run 'test name'"

4. **View Test Results**:
   - Test results appear in the "Run" panel at the bottom
   - Green checkmark = passed
   - Red X = failed
   - Click on any test to see details

### Option 2: Command Line

```bash
# Navigate to project directory
cd NewsApp-master

# Run all unit tests
./gradlew testDebugUnitTest

# Run with test report
./gradlew testDebugUnitTest --continue

# View test results
open app/build/reports/tests/testDebugUnitTest/index.html
```

### Option 3: Gradle Panel in Android Studio

1. Open "Gradle" panel (right side of Android Studio)
2. Navigate to: `app` → `Tasks` → `verification`
3. Double-click `testDebugUnitTest`

## Test Dependencies

The following testing libraries are used:

```kotlin
// Unit Testing
testImplementation("junit:junit:4.13.2")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
testImplementation("io.mockk:mockk:1.13.8")
testImplementation("androidx.arch.core:core-testing:2.2.0")
testImplementation("app.cash.turbine:turbine:1.0.0")

// Hilt Testing
testImplementation("com.google.dagger:hilt-android-testing:2.48")
kaptTest("com.google.dagger:hilt-android-compiler:2.48")
```

## Testing Best Practices Used

1. **AAA Pattern**: Arrange, Act, Assert
2. **Descriptive Names**: Test names clearly describe what is being tested
3. **Mocking**: Using MockK for dependency mocking
4. **Coroutine Testing**: Using TestDispatcher for coroutine tests
5. **Isolation**: Each test is independent
6. **Coverage**: Tests cover success, failure, and edge cases
7. **Fast Execution**: Unit tests run quickly without Android framework

## Test Structure

Each test follows this structure:

```kotlin
@Test
fun `descriptive test name in backticks`() = testScope.runTest {
    // Given (Arrange)
    val input = "test data"
    
    // When (Act)
    val result = functionUnderTest(input)
    
    // Then (Assert)
    assertEquals(expected, result)
}
```

## Continuous Integration

These tests can be integrated into CI/CD pipelines:

```yaml
# Example GitHub Actions
- name: Run Unit Tests
  run: ./gradlew testDebugUnitTest
  
- name: Upload Test Results
  uses: actions/upload-artifact@v2
  with:
    name: test-results
    path: app/build/reports/tests/
```

## Coverage Report

To generate a test coverage report:

```bash
./gradlew testDebugUnitTestCoverage
open app/build/reports/coverage/test/debug/index.html
```

## Troubleshooting

### Tests Not Running

1. **Sync Gradle**: File → Sync Project with Gradle Files
2. **Clean Build**: Build → Clean Project
3. **Invalidate Caches**: File → Invalidate Caches / Restart

### MockK Issues

If you encounter MockK errors:
- Ensure you're using the latest version
- Add `@OptIn(ExperimentalCoroutinesApi::class)` for coroutine tests

### Coroutine Tests Failing

- Use `testScope.runTest { }` for suspending functions
- Call `advanceUntilIdle()` to execute all pending coroutines

## Future Test Additions

Potential areas for additional testing:

1. **Integration Tests**: Test API integration with real endpoints
2. **UI Tests**: Compose UI testing with `@Composable` functions
3. **DataStore Tests**: Test preference storage
4. **Navigation Tests**: Test navigation flows
5. **End-to-End Tests**: Full user journey tests

## Conclusion

This comprehensive test suite ensures the NewsApp is reliable, maintainable, and bug-free. All critical business logic in the ViewModel is thoroughly tested, following Android testing best practices.

---

**Last Updated**: October 21, 2025  
**Test Framework**: JUnit 4  
**Mocking Library**: MockK  
**Total Tests**: 49
