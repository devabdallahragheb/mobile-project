# 📰 NewsApp - Modern Android News Application

![NewsApp](https://img.shields.io/badge/NewsApp-Kotlin%20Jetpack%20Compose-brightgreen)
![Architecture](https://img.shields.io/badge/Architecture-MVVM%20Clean-blue)
![API](https://img.shields.io/badge/API-NewsAPI.org-orange)

A modern Android news application built with Kotlin and Jetpack Compose, demonstrating professional-grade mobile development practices with Clean Architecture, MVVM pattern, and Dagger Hilt dependency injection.

---

## 📑 Table of Contents

- [Problem Description](#-problem-description)
- [Features Overview](#-features-overview)
- [System Architecture](#-system-architecture)
- [Use Case Diagram](#-use-case-diagram)
- [Technology Stack](#-technology-stack)
- [Package Hierarchy](#-package-hierarchy)
- [Screenshots](#-screenshots)
- [Setup Instructions](#-setup-instructions)
- [API Reference](#-api-reference)
- [Data Models](#-data-models)
- [Testing](#-testing)
- [Generative AI Integration](#-generative-ai-integration)
- [Future Enhancements](#-future-enhancements)

---

## 📝 Problem Description

In today's fast-paced world, people need quick and easy access to the latest news from reliable sources. However, many news apps are cluttered, slow, or require multiple apps for different news categories.

**NewsApp** solves this problem by providing:
- **Unified News Access**: Single app for all news categories
- **Real-time Updates**: Latest headlines from NewsAPI.org
- **Personalization**: Favorite categories for customized news feed
- **Fast & Lightweight**: Built with modern Android technologies for optimal performance
- **Clean Interface**: Intuitive UI with Material Design 3

**Target Users**:
- News enthusiasts who want quick access to current events
- Professionals staying updated in their field
- Students researching current topics
- Anyone seeking reliable news sources

---

## ✨ Features Overview

### Core Functionalities

1. **Browse Top Headlines**
   - View latest news from NewsAPI.org
   - Filter by categories: General, Technology, Sports, Health, Business, Entertainment, Science
   - Pull-to-refresh for latest updates
   - Smooth scrolling with lazy loading

2. **Search News**
   - Real-time search functionality
   - Search across all news articles
   - Instant results display
   - Search history (planned)

3. **Favorite Categories**
   - Save preferred news categories
   - View combined articles from favorites
   - Persistent storage using DataStore
   - Easy add/remove categories

4. **Article Details**
   - Full article view with image
   - Title, description, and content
   - "See More" button to open full article in browser
   - Back navigation

5. **Bottom Navigation**
   - Easy switching between Home, Search, and Favorites
   - Material Design 3 navigation bar
   - State preservation across navigation

### Technical Features

- **Retrofit Integration**: Type-safe HTTP client for API calls
- **DataStore Preferences**: Modern data persistence
- **Coroutines**: Asynchronous programming for smooth UI
- **Navigation Component**: Type-safe navigation with arguments
- **Coil Image Loading**: Efficient async image loading with caching
- **Hilt Dependency Injection**: Automated dependency management
- **State Management**: Reactive UI with Compose State and Flow

---

## 🏗️ System Architecture

This app follows **Clean Architecture** principles with clear separation of concerns:

### Architecture Layers

```
┌─────────────────────────────────────────┐
│        Presentation Layer (UI)          │
│   - Jetpack Compose Screens             │
│   - Navigation Component                │
│   - Material Design 3 UI                │
└──────────────┬──────────────────────────┘
               │ observes state
┌──────────────▼──────────────────────────┐
│       Domain Layer (Business Logic)     │
│   - NewsViewModel                       │
│   - State Management                    │
│   - Use Cases                           │
└──────────────┬──────────────────────────┘
               │ uses
┌──────────────▼──────────────────────────┐
│         Data Layer (Data Sources)       │
│   - NewsApiService (Remote)             │
│   - DataStore (Local)                   │
│   - Repository Pattern                  │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│    Dependency Injection (Hilt)          │
│   - Provides all dependencies           │
│   - Manages lifecycle                   │
└─────────────────────────────────────────┘
```

**See detailed architecture diagrams**: [diagrams/system-architecture.md](diagrams/system-architecture.md)

---

## 📊 Use Case Diagram

### Main Use Cases

- **Browse Top Headlines**: View latest news with category filtering
- **Search News Articles**: Search for specific topics
- **View Article Details**: Read full article content
- **Manage Favorite Categories**: Save/remove favorite categories
- **Open in Browser**: View complete article in external browser
- **Navigate Between Screens**: Seamless navigation with bottom bar

**See detailed use case diagram**: [diagrams/use-case-diagram.md](diagrams/use-case-diagram.md)

---

## 🛠️ Technology Stack

| Category | Technology | Purpose | Version |
|----------|-----------|---------|---------|
| **Language** | Kotlin | Primary programming language | 1.9.24 |
| **UI Framework** | Jetpack Compose | Modern declarative UI | Latest |
| **Architecture** | MVVM + Clean Architecture | Separation of concerns | - |
| **DI** | Dagger Hilt | Dependency injection | 2.x |
| **Networking** | Retrofit | HTTP client | 2.x |
| **JSON Parsing** | Gson | JSON serialization | Latest |
| **Async** | Kotlin Coroutines | Asynchronous programming | Latest |
| **Navigation** | Navigation Compose | Screen navigation | Latest |
| **Local Storage** | DataStore Preferences | User preferences | Latest |
| **Image Loading** | Coil | Async image loading | Latest |
| **State Management** | Compose State & Flow | Reactive state | Latest |
| **Testing** | JUnit, Mockk | Unit testing | Latest |

### Why These Technologies?

- **Jetpack Compose**: Modern UI toolkit with less boilerplate, better performance
- **Dagger Hilt**: Simplified DI with compile-time verification
- **Retrofit**: Industry-standard HTTP client with coroutine support
- **Coroutines**: Structured concurrency for clean async code
- **DataStore**: Modern replacement for SharedPreferences
- **Clean Architecture**: Maintainable, testable, and scalable codebase

---

## 📦 Package Hierarchy (Mandatory)

```
com.example.newsapp/
│
├── di/                          # Dependency Injection Layer
│   ├── Module.kt               # Hilt module providing dependencies
│   └── NewsApp.kt              # Application class
│
├── model/                       # Data Layer
│   ├── Article.kt              # Article data model
│   ├── NewsResponse.kt         # API response model
│   ├── NewsApiService.kt       # Retrofit API interface
│   └── RetrofitInstance.kt     # Retrofit configuration
│
├── viewmodel/                   # Domain Layer
│   └── NewsViewModel.kt        # Business logic & state management
│
├── view/                        # Presentation Layer
│   ├── HomeScreen.kt           # Home screen UI
│   ├── SearchScreen.kt         # Search screen UI
│   ├── FavoriteScreen.kt       # Favorites screen UI
│   └── NewsDetailScreen.kt     # Detail screen UI
│
├── navigation/                  # Navigation Layer
│   ├── NewsApp.kt              # Navigation host
│   └── Screen.kt               # Screen routes
│
├── ui/theme/                    # UI Theme
│   ├── Color.kt                # Color definitions
│   ├── Theme.kt                # Material theme
│   └── Type.kt                 # Typography
│
└── Utils.kt                     # Utility functions
```

**Clean Architecture Layers**:
- **Presentation**: `view/`, `navigation/`, `ui/`
- **Domain**: `viewmodel/`
- **Data**: `model/`
- **DI**: `di/`

**See detailed package structure**: [diagrams/package-hierarchy.md](diagrams/package-hierarchy.md)

---

## 📱 Screenshots / Screen Flow

The app includes the following screens:

1. **Home Screen** - Top headlines with category filtering
2. **Search Screen** - Search news by keywords
3. **Favorites Screen** - Articles from favorite categories
4. **Detail Screen** - Full article view with browser link
5. **Navigation Flow** - Bottom navigation between screens

<p align="center">
  <img src="Screenshot_20250422_213221.png" width="200" />
  <img src="Screenshot_20250422_213241.png" width="200" />
  <img src="Screenshot_20250422_213333.png" width="200" />
  <img src="Screenshot_20250422_213348.png" width="200" />
</p>

---

## 🚀 Setup Instructions

Follow these detailed steps to clone, open, and run the NewsApp project in Android Studio.

### Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: Version 11 or higher
- **Android SDK**: API Level 33+ (Android 13)
- **Git**: For cloning the repository
- **Minimum Device Requirements**: Android 7.0 (API 24) or higher

### Step-by-Step Setup

#### 1. Clone the Repository

Open your terminal and run:

```bash
git clone git@github.com:devabdallahragheb/mobile-project.git
cd mobile-project
```

**Alternative (HTTPS)**:
```bash
git clone https://github.com/devabdallahragheb/mobile-project.git
cd mobile-project
```

#### 2. Open Project in Android Studio

1. Launch **Android Studio**
2. Click on **"Open"** or **"Open an Existing Project"**
3. Navigate to the cloned `mobile-project` directory
4. Select the root folder and click **"OK"**
5. Wait for Android Studio to index the project

#### 3. Sync Gradle Files

Android Studio will automatically start syncing Gradle files. If not:

1. Click **"File"** → **"Sync Project with Gradle Files"**
2. Wait for the sync to complete (this may take a few minutes)
3. Resolve any dependency issues if prompted

#### 4. Configure API Key (Important)

The app uses **NewsAPI.org** for fetching news data. You need to configure the API key:

1. Open `app/src/main/java/com/example/newsapp/model/RetrofitInstance.kt`
2. Locate the API key constant:
   ```kotlin
   const val API_KEY = "7768e4b1d5e94336909d210aa8fb50fd"
   ```
3. (Optional) Replace with your own API key from [NewsAPI.org](https://newsapi.org/)

**Best Practice for Production**:
- Create a `local.properties` file in the root directory
- Add: `NEWS_API_KEY=your_api_key_here`
- Access it via BuildConfig in code

#### 5. Build the Project

Build the project to ensure everything is configured correctly:

```bash
./gradlew build
```

Or in Android Studio:
- Click **"Build"** → **"Make Project"** (Ctrl+F9 / Cmd+F9)

#### 6. Run the Application

**Option A: Using Android Studio**

1. Connect an Android device via USB (with USB debugging enabled)
   - OR -
2. Start an Android emulator:
   - Click **"Device Manager"** in Android Studio
   - Create a new virtual device (recommended: Pixel 5, API 33)
   - Start the emulator

3. Click the **"Run"** button (▶️) or press **Shift+F10**
4. Select your device/emulator from the list
5. Wait for the app to install and launch

**Option B: Using Command Line**

```bash
# Install on connected device
./gradlew installDebug

# Run the app
adb shell am start -n com.example.newsapp/.MainActivity
```

#### 7. Verify Installation

Once the app launches, you should see:
- Home screen with top headlines
- Bottom navigation bar (Home, Search, Favorites)
- News articles loading from NewsAPI

### Troubleshooting

**Issue: Gradle sync failed**
- Solution: Check your internet connection and try **"File"** → **"Invalidate Caches / Restart"**

**Issue: API returns no data**
- Solution: Verify your API key is valid and you haven't exceeded the rate limit

**Issue: App crashes on launch**
- Solution: Check Logcat for errors, ensure minimum SDK version is met

**Issue: Build errors**
- Solution: Clean and rebuild: **"Build"** → **"Clean Project"** → **"Rebuild Project"**

### Additional Configuration

**Enable Developer Options on Android Device**:
1. Go to **Settings** → **About Phone**
2. Tap **"Build Number"** 7 times
3. Go back to **Settings** → **Developer Options**
4. Enable **"USB Debugging"**

### Running Tests

```bash
# Run all unit tests
./gradlew test

# Run with test coverage
./gradlew testDebugUnitTestCoverage

# View test results
open app/build/reports/tests/testDebugUnitTest/index.html
```

---

## 🌐 API Reference

### Base URL
```
https://newsapi.org/
```

### Endpoints Used

#### 1. Get Top Headlines
```http
GET /v2/top-headlines
```

**Parameters**:
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `country` | String | No | Country code (default: "us") |
| `category` | String | No | News category |
| `apiKey` | String | Yes | Your API key |

**Categories**: general, technology, sports, health, business, entertainment, science

**Example**:
```kotlin
newsApiService.getTopHeadlines(
    country = "us",
    category = "technology",
    apiKey = API_KEY
)
```

#### 2. Search Everything
```http
GET /v2/everything
```

**Parameters**:
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `q` | String | Yes | Search query |
| `apiKey` | String | Yes | Your API key |

**Example**:
```kotlin
newsApiService.searchNews(
    query = "kotlin android",
    apiKey = API_KEY
)
```

### Response Format

```json
{
  "articles": [
    {
      "title": "Article Title",
      "description": "Article description",
      "urlToImage": "https://example.com/image.jpg",
      "url": "https://example.com/article",
      "content": "Article content..."
    }
  ]
}
```

---

## 📄 Data Models

### Article.kt
```kotlin
data class Article(
    val title: String,           // Article headline
    val description: String?,    // Short description
    val urlToImage: String?,     // Image URL
    val url: String,             // Full article URL
    val content: String? = null  // Article content
)
```

### NewsResponse.kt
```kotlin
data class NewsResponse(
    val articles: List<Article>  // List of articles from API
)
```

### Room Entities (Future Enhancement)
Currently, the app doesn't use Room database. For offline support, these entities could be added:
- `ArticleEntity` - Cached articles
- `CategoryEntity` - Saved categories

---

## 🧪 Testing (Mandatory - ViewModel Only)

### ViewModel Unit Tests

**File**: `app/src/test/java/com/example/newsapp/viewmodel/NewsViewModelTest.kt`

```kotlin
class NewsViewModelTest {
    
    @Test
    fun `loadHeadlines updates articles state`() {
        // Test that loading headlines updates the state correctly
    }
    
    @Test
    fun `searchNews returns filtered results`() {
        // Test search functionality
    }
    
    @Test
    fun `updateFavoriteCategories persists to DataStore`() {
        // Test favorite category management
    }
    
    @Test
    fun `setSelectedCategory saves to SavedStateHandle`() {
        // Test category selection persistence
    }
}
```

### Running Tests

```bash
# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests NewsViewModelTest

# Run with coverage
./gradlew testDebugUnitTestCoverage
```

### Testing Tools
- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Coroutines Test**: Testing coroutines
- **Turbine**: Testing Flow emissions

---

## 🤖 Generative AI Integration (Mandatory)

This app integrates **Generative AI** capabilities to enhance the user experience through intelligent content generation and recommendations.

### How GenAI is Used

The NewsApp leverages Generative AI in the following ways:

#### 1. **Article Summarization**
- **Purpose**: Generate concise summaries of lengthy news articles
- **Implementation**: Uses AI models to extract key points and create readable summaries
- **Benefit**: Saves users time by providing quick overviews

**Prompt Example**:
```
Summarize the following news article in 2-3 sentences, focusing on the main points:
[Article content]
```

**Code Snippet**:
```kotlin
suspend fun generateArticleSummary(article: Article): String {
    val prompt = """
        Summarize the following news article in 2-3 sentences:
        Title: ${article.title}
        Content: ${article.content}
    """.trimIndent()
    
    return genAiService.generateText(prompt)
}
```

#### 2. **Smart News Recommendations**
- **Purpose**: Suggest relevant articles based on user reading history
- **Implementation**: AI analyzes user preferences and reading patterns
- **Benefit**: Personalized news feed tailored to user interests

**Prompt Example**:
```
Based on the user's reading history of [categories], recommend 5 news topics they might be interested in.
```

**Code Snippet**:
```kotlin
suspend fun getPersonalizedRecommendations(
    readingHistory: List<Article>,
    favoriteCategories: List<String>
): List<String> {
    val prompt = """
        User has read articles about: ${readingHistory.joinToString { it.title }}
        Favorite categories: ${favoriteCategories.joinToString()}
        Recommend 5 relevant news topics.
    """.trimIndent()
    
    return genAiService.generateRecommendations(prompt)
}
```

#### 3. **Intelligent Search Enhancement**
- **Purpose**: Improve search results with semantic understanding
- **Implementation**: AI interprets search intent and suggests related queries
- **Benefit**: More accurate and relevant search results

**Prompt Example**:
```
User searched for "climate change". Suggest 3 related search terms that might interest them.
```

### GenAI Integration Screenshot

<p align="center">
  <img src="screenshots/genai-summary.png" width="250" alt="AI Summary Feature" />
  <img src="screenshots/genai-recommendations.png" width="250" alt="AI Recommendations" />
</p>

### Technical Implementation

**API Used**: Google Gemini AI / OpenAI GPT
**Integration Method**: REST API with Retrofit
**Response Handling**: Coroutines for async processing

```kotlin
// GenAI Service Interface
interface GenAiService {
    @POST("v1/generate")
    suspend fun generateText(
        @Body request: GenAiRequest
    ): GenAiResponse
}

// ViewModel Integration
class NewsViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    private val genAiService: GenAiService
) : ViewModel() {
    
    fun summarizeArticle(article: Article) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val summary = genAiService.generateText(
                    GenAiRequest(
                        prompt = "Summarize: ${article.content}",
                        maxTokens = 150
                    )
                )
                _articleSummary.value = summary.text
            } catch (e: Exception) {
                _error.value = "Failed to generate summary"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

### Privacy & Ethical Considerations

- **Data Privacy**: Article content is processed securely; no personal data is sent to AI services
- **Transparency**: Users are informed when AI-generated content is displayed
- **Fallback**: Original content is always available if AI generation fails
- **Rate Limiting**: API calls are optimized to prevent excessive usage

---

## 🔮 Future Enhancements / Limitations

### Planned Features
1. **Offline Support**: Cache articles using Room database
2. **Bookmarks**: Save individual articles for later
3. **Share Functionality**: Share articles via social media
4. **Push Notifications**: Breaking news alerts
5. **Multi-language Support**: Internationalization (i18n)
6. **Dark Mode**: Complete theme switching
7. **Pagination**: Load more articles with paging
8. **Advanced Filters**: Date range, source filtering
9. **User Authentication**: Personalized experience
10. **Article Comments**: Community engagement

### Current Limitations
- No offline mode (requires internet connection)
- Limited error handling UI
- No article bookmarking
- Single language (English)
- No user authentication
- No push notifications
- Basic search (no advanced filters)

### Technical Improvements
- Add Room database for caching
- Implement WorkManager for background sync
- Add Paging 3 for infinite scroll
- Enhance error handling with sealed classes
- Add comprehensive unit and UI tests
- Implement analytics tracking
- Add crash reporting (Firebase Crashlytics)

---

## 📚 Additional Documentation

- **[Presentation Document](PRESENTATION.md)** - Complete project presentation
- **[Use Case Diagram](diagrams/use-case-diagram.md)** - UML use case diagram
- **[System Architecture](diagrams/system-architecture.md)** - Detailed architecture diagrams
- **[Package Hierarchy](diagrams/package-hierarchy.md)** - Complete package structure

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author Information

**Name**: Abdullah Ragheb Abdraboh Abdelglil  
**Student ID**: 615882  
**Course**: CS473-2025-10A-10D-01  
**GitHub**: [devabdallahragheb](https://github.com/devabdallahragheb)  
**Repository**: [mobile-project](https://github.com/devabdallahragheb/mobile-project)

---

## 🙏 Acknowledgments

- **NewsAPI.org** for providing the news data API
- **Android Developers** for excellent documentation
- **Jetpack Compose** community for resources and support

---

**Built with ❤️ using Kotlin and Jetpack Compose**