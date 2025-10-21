# System Architecture - News App

## Clean Architecture Layers

```mermaid
graph TB
    subgraph "Presentation Layer"
        UI[Jetpack Compose UI]
        VM[ViewModels]
        NAV[Navigation]
        
        UI --> VM
        NAV --> UI
    end
    
    subgraph "Domain Layer"
        UC[Use Cases / Business Logic]
        MODELS[Domain Models]
        
        UC --> MODELS
    end
    
    subgraph "Data Layer"
        REPO[Repository Pattern]
        API[Remote Data Source - NewsAPI]
        LOCAL[Local Data Source - DataStore]
        
        REPO --> API
        REPO --> LOCAL
    end
    
    subgraph "DI Layer"
        HILT[Dagger Hilt Modules]
    end
    
    VM --> UC
    UC --> REPO
    HILT -.->|provides| VM
    HILT -.->|provides| REPO
    HILT -.->|provides| API
    
    style UI fill:#e1f5ff,stroke:#01579b,stroke-width:2px
    style VM fill:#e1f5ff,stroke:#01579b,stroke-width:2px
    style NAV fill:#e1f5ff,stroke:#01579b,stroke-width:2px
    style UC fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    style MODELS fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    style REPO fill:#e8f5e9,stroke:#1b5e20,stroke-width:2px
    style API fill:#e8f5e9,stroke:#1b5e20,stroke-width:2px
    style LOCAL fill:#e8f5e9,stroke:#1b5e20,stroke-width:2px
    style HILT fill:#fff3e0,stroke:#e65100,stroke-width:2px
```

## MVVM Architecture Pattern

```mermaid
graph LR
    subgraph "View Layer"
        HOME[HomeScreen.kt]
        SEARCH[SearchScreen.kt]
        FAV[FavoriteScreen.kt]
        DETAIL[NewsDetailScreen.kt]
    end
    
    subgraph "ViewModel Layer"
        NEWSVM[NewsViewModel]
        STATE[State Management]
        
        NEWSVM --> STATE
    end
    
    subgraph "Model Layer"
        SERVICE[NewsApiService]
        RETROFIT[RetrofitInstance]
        ARTICLE[Article Model]
        RESPONSE[NewsResponse Model]
        DS[DataStore]
        
        SERVICE --> RETROFIT
        SERVICE --> ARTICLE
        SERVICE --> RESPONSE
    end
    
    HOME -->|observes state| NEWSVM
    SEARCH -->|observes state| NEWSVM
    FAV -->|observes state| NEWSVM
    DETAIL -->|receives data| ARTICLE
    
    NEWSVM -->|calls| SERVICE
    NEWSVM -->|reads/writes| DS
    
    style HOME fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style SEARCH fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style FAV fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style DETAIL fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style NEWSVM fill:#c8e6c9,stroke:#388e3c,stroke-width:2px
    style STATE fill:#c8e6c9,stroke:#388e3c,stroke-width:2px
    style SERVICE fill:#ffccbc,stroke:#d84315,stroke-width:2px
    style RETROFIT fill:#ffccbc,stroke:#d84315,stroke-width:2px
    style ARTICLE fill:#ffccbc,stroke:#d84315,stroke-width:2px
    style RESPONSE fill:#ffccbc,stroke:#d84315,stroke-width:2px
    style DS fill:#ffccbc,stroke:#d84315,stroke-width:2px
```

## Detailed Component Architecture

```mermaid
graph TB
    subgraph "UI Components - Jetpack Compose"
        A1[HomeScreen]
        A2[SearchScreen]
        A3[FavoriteScreen]
        A4[NewsDetailScreen]
        A5[NewsApp Navigation]
        A6[Bottom Navigation Bar]
    end
    
    subgraph "ViewModel - State Management"
        B1[NewsViewModel]
        B2[articles: State<List<Article>>]
        B3[searchArticles: State<List<Article>>]
        B4[favoriteArticles: State<List<Article>>]
        B5[selectedCategory: State<String>]
        B6[favoriteCategoriesFlow: Flow<List<String>>]
    end
    
    subgraph "Business Logic"
        C1[loadHeadlines]
        C2[searchNews]
        C3[loadFavoriteArticles]
        C4[updateFavoriteCategories]
        C5[setSelectedCategory]
    end
    
    subgraph "Data Sources"
        D1[NewsApiService]
        D2[getTopHeadlines]
        D3[searchNews]
        D4[DataStore Preferences]
    end
    
    subgraph "Network Layer"
        E1[Retrofit Client]
        E2[Gson Converter]
        E3[Coroutines]
        E4[NewsAPI.org]
    end
    
    subgraph "Dependency Injection"
        F1[Hilt Module]
        F2[Provides Retrofit]
        F3[Provides NewsApiService]
        F4[Provides API Key]
        F5[Provides Context]
    end
    
    subgraph "Data Models"
        G1[Article]
        G2[NewsResponse]
    end
    
    A1 --> B1
    A2 --> B1
    A3 --> B1
    A4 --> G1
    A5 --> A1
    A5 --> A2
    A5 --> A3
    A5 --> A4
    A6 --> A5
    
    B1 --> B2
    B1 --> B3
    B1 --> B4
    B1 --> B5
    B1 --> B6
    
    B1 --> C1
    B1 --> C2
    B1 --> C3
    B1 --> C4
    B1 --> C5
    
    C1 --> D1
    C2 --> D1
    C3 --> D1
    C4 --> D4
    
    D1 --> D2
    D1 --> D3
    D2 --> G2
    D3 --> G2
    G2 --> G1
    
    D1 --> E1
    E1 --> E2
    E1 --> E3
    E1 --> E4
    
    F1 --> F2
    F1 --> F3
    F1 --> F4
    F1 --> F5
    F2 --> E1
    F3 --> D1
    F4 --> B1
    F5 --> B1
    
    style A1 fill:#e3f2fd,stroke:#1565c0
    style A2 fill:#e3f2fd,stroke:#1565c0
    style A3 fill:#e3f2fd,stroke:#1565c0
    style A4 fill:#e3f2fd,stroke:#1565c0
    style A5 fill:#e3f2fd,stroke:#1565c0
    style A6 fill:#e3f2fd,stroke:#1565c0
    style B1 fill:#f1f8e9,stroke:#558b2f
    style C1 fill:#fff9c4,stroke:#f57f17
    style C2 fill:#fff9c4,stroke:#f57f17
    style C3 fill:#fff9c4,stroke:#f57f17
    style C4 fill:#fff9c4,stroke:#f57f17
    style C5 fill:#fff9c4,stroke:#f57f17
    style D1 fill:#fce4ec,stroke:#c2185b
    style E1 fill:#e0f2f1,stroke:#00695c
    style F1 fill:#fff3e0,stroke:#ef6c00
    style G1 fill:#f3e5f5,stroke:#7b1fa2
    style G2 fill:#f3e5f5,stroke:#7b1fa2
```

## Data Flow Architecture

```mermaid
sequenceDiagram
    participant User
    participant UI as Compose UI
    participant VM as NewsViewModel
    participant API as NewsApiService
    participant Retrofit
    participant NewsAPI as NewsAPI.org
    participant DS as DataStore
    
    User->>UI: Open App
    UI->>VM: Observe articles state
    VM->>VM: Initialize with saved category
    VM->>API: getTopHeadlines(category, apiKey)
    API->>Retrofit: HTTP GET Request
    Retrofit->>NewsAPI: /v2/top-headlines?category=general
    NewsAPI-->>Retrofit: JSON Response
    Retrofit-->>API: NewsResponse
    API-->>VM: List<Article>
    VM->>VM: Update _articles.value
    VM-->>UI: State change notification
    UI-->>User: Display articles
    
    User->>UI: Select Category
    UI->>VM: setSelectedCategory("technology")
    VM->>DS: Save to SavedStateHandle
    VM->>API: getTopHeadlines("technology", apiKey)
    API->>Retrofit: HTTP GET Request
    Retrofit->>NewsAPI: /v2/top-headlines?category=technology
    NewsAPI-->>Retrofit: JSON Response
    Retrofit-->>API: NewsResponse
    API-->>VM: List<Article>
    VM->>VM: Update _articles.value
    VM-->>UI: State change notification
    UI-->>User: Display filtered articles
    
    User->>UI: Search "kotlin"
    UI->>VM: searchNews("kotlin")
    VM->>API: searchNews("kotlin", apiKey)
    API->>Retrofit: HTTP GET Request
    Retrofit->>NewsAPI: /v2/everything?q=kotlin
    NewsAPI-->>Retrofit: JSON Response
    Retrofit-->>API: NewsResponse
    API-->>VM: List<Article>
    VM->>VM: Update _searchArticles.value
    VM-->>UI: State change notification
    UI-->>User: Display search results
    
    User->>UI: Add to Favorites
    UI->>VM: updateFavoriteCategories("sports", false)
    VM->>DS: Edit DataStore preferences
    DS->>DS: Serialize to JSON
    DS-->>VM: Preferences updated
    VM->>VM: Emit favoriteCategoriesFlow
    VM-->>UI: State change notification
    UI-->>User: Favorites updated
```

## Dependency Injection Flow

```mermaid
graph TB
    subgraph "Application Scope"
        APP[NewsApplication @HiltAndroidApp]
    end
    
    subgraph "Hilt Module @InstallIn SingletonComponent"
        M1[provideRetrofit]
        M2[provideNewsApiService]
        M3[provideApiKey]
        M4[provideContext]
    end
    
    subgraph "ViewModel @HiltViewModel"
        VM[NewsViewModel @Inject constructor]
        P1[newsApiService: NewsApiService]
        P2[apiKey: String]
        P3[context: Context]
        P4[savedStateHandle: SavedStateHandle]
    end
    
    subgraph "Composable Functions"
        C1[NewsApp composable]
        C2[viewModel: NewsViewModel = hiltViewModel]
    end
    
    APP --> M1
    APP --> M2
    APP --> M3
    APP --> M4
    
    M1 -->|provides| M2
    M2 -->|provides| P1
    M3 -->|provides| P2
    M4 -->|provides| P3
    
    P1 --> VM
    P2 --> VM
    P3 --> VM
    P4 --> VM
    
    VM --> C2
    C2 --> C1
    
    style APP fill:#ffebee,stroke:#c62828,stroke-width:3px
    style M1 fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    style M2 fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    style M3 fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    style M4 fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    style VM fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px
    style C1 fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    style C2 fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
```

## Navigation Architecture

```mermaid
graph TB
    subgraph "Navigation Host"
        NH[NavHost - NewsApp.kt]
    end
    
    subgraph "Navigation Destinations"
        D1[Screen.Home]
        D2[Screen.Search]
        D3[Screen.Favorites]
        D4[news_detail with arguments]
    end
    
    subgraph "Bottom Navigation"
        BN[NavigationBar]
        BN1[Home Icon]
        BN2[Search Icon]
        BN3[Favorites Icon]
    end
    
    subgraph "Screen Composables"
        S1[HomeScreen]
        S2[SearchScreen]
        S3[FavoriteScreen]
        S4[NewsDetailScreen]
    end
    
    subgraph "Navigation Arguments"
        A1[title: String]
        A2[description: String?]
        A3[urlToImage: String?]
        A4[url: String]
        A5[content: String?]
    end
    
    NH --> D1
    NH --> D2
    NH --> D3
    NH --> D4
    
    D1 --> S1
    D2 --> S2
    D3 --> S3
    D4 --> S4
    
    BN --> BN1
    BN --> BN2
    BN --> BN3
    
    BN1 -.->|navigate| D1
    BN2 -.->|navigate| D2
    BN3 -.->|navigate| D3
    
    S1 -.->|onArticleClick| D4
    S2 -.->|onArticleClick| D4
    S3 -.->|onArticleClick| D4
    
    D4 --> A1
    D4 --> A2
    D4 --> A3
    D4 --> A4
    D4 --> A5
    
    A1 --> S4
    A2 --> S4
    A3 --> S4
    A4 --> S4
    A5 --> S4
    
    style NH fill:#e1bee7,stroke:#6a1b9a,stroke-width:3px
    style D1 fill:#c5cae9,stroke:#3949ab,stroke-width:2px
    style D2 fill:#c5cae9,stroke:#3949ab,stroke-width:2px
    style D3 fill:#c5cae9,stroke:#3949ab,stroke-width:2px
    style D4 fill:#c5cae9,stroke:#3949ab,stroke-width:2px
    style BN fill:#b2dfdb,stroke:#00796b,stroke-width:2px
    style S1 fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style S2 fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style S3 fill:#bbdefb,stroke:#1976d2,stroke-width:2px
    style S4 fill:#bbdefb,stroke:#1976d2,stroke-width:2px
```

## Layer Responsibilities

### Presentation Layer (UI)
**Location**: `view/` package

**Components**:
- `HomeScreen.kt` - Displays top headlines with category filtering
- `SearchScreen.kt` - Search interface for news articles
- `FavoriteScreen.kt` - Shows articles from favorite categories
- `NewsDetailScreen.kt` - Detailed article view
- `NewsApp.kt` - Navigation host and bottom navigation

**Responsibilities**:
- Render UI using Jetpack Compose
- Observe ViewModel state
- Handle user interactions
- Navigate between screens
- Display loading/error states

**Technologies**:
- Jetpack Compose
- Material Design 3
- Coil for image loading
- Navigation Component

### Domain Layer (Business Logic)
**Location**: `viewmodel/` package

**Components**:
- `NewsViewModel.kt` - Central business logic and state management

**Responsibilities**:
- Manage application state
- Execute business logic
- Coordinate data operations
- Handle coroutine scopes
- Persist UI state
- Transform data for UI

**Technologies**:
- ViewModel
- Kotlin Coroutines
- StateFlow/State
- SavedStateHandle
- Dagger Hilt

### Data Layer
**Location**: `model/` and `di/` packages

**Components**:
- `NewsApiService.kt` - API interface
- `RetrofitInstance.kt` - Network configuration
- `Article.kt` - Data model
- `NewsResponse.kt` - API response model
- `Module.kt` - Dependency injection configuration

**Responsibilities**:
- Fetch data from remote API
- Store/retrieve local preferences
- Provide data to domain layer
- Handle network requests
- Serialize/deserialize data

**Technologies**:
- Retrofit
- Gson
- DataStore
- Coroutines
- Dagger Hilt

### DI Layer
**Location**: `di/` package

**Components**:
- `Module.kt` - Hilt module providing dependencies
- `NewsApp.kt` - Application class with @HiltAndroidApp

**Responsibilities**:
- Provide singleton instances
- Inject dependencies
- Manage object lifecycle
- Configure application scope

**Technologies**:
- Dagger Hilt
- Singleton pattern

## Architecture Benefits

### Separation of Concerns
- Each layer has a single responsibility
- UI doesn't know about data sources
- Business logic is independent of UI
- Easy to modify individual layers

### Testability
- ViewModels can be tested without UI
- API service can be mocked
- Business logic is isolated
- Unit tests for each layer

### Maintainability
- Clear code organization
- Easy to locate functionality
- Consistent patterns throughout
- Well-defined boundaries

### Scalability
- Easy to add new features
- Can extend without breaking existing code
- Modular architecture
- Reusable components

### Dependency Management
- Automatic dependency injection
- No manual object creation
- Compile-time verification
- Reduced boilerplate
