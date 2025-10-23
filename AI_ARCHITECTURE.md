# AI Architecture Diagram

## System Architecture with OpenAI Integration

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER INTERFACE                          │
│                     (Jetpack Compose)                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────────────────────────────────────┐      │
│  │            AIInsightsCard.kt                         │      │
│  │  ┌────────────────────────────────────────────┐     │      │
│  │  │  [AI Insights Header]                      │     │      │
│  │  │  ┌──────────────────────────────────────┐ │     │      │
│  │  │  │ 🤖 Generate AI Analysis Button       │ │     │      │
│  │  │  └──────────────────────────────────────┘ │     │      │
│  │  │                                            │     │      │
│  │  │  📝 AI Summary:                           │     │      │
│  │  │  "This article discusses..."              │     │      │
│  │  │                                            │     │      │
│  │  │  😊 Sentiment: Positive (High confidence) │     │      │
│  │  │  "The article has an optimistic tone..."  │     │      │
│  │  │                                            │     │      │
│  │  │  💡 Key Insights:                         │     │      │
│  │  │  1. First insight...                      │     │      │
│  │  │  2. Second insight...                     │     │      │
│  │  │  3. Third insight...                      │     │      │
│  │  └────────────────────────────────────────────┘     │      │
│  └──────────────────────────────────────────────────────┘      │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                      VIEW MODEL LAYER                           │
│                    (NewsViewModel.kt)                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  State Management:                                              │
│  • _aiSummary: State<AISummary?>                               │
│  • _sentimentAnalysis: State<SentimentAnalysis?>               │
│  • _articleInsights: State<ArticleInsights?>                   │
│  • _isAILoading: State<Boolean>                                │
│                                                                 │
│  Functions:                                                     │
│  • generateAISummary(article: Article)                         │
│  • analyzeSentiment(article: Article)                          │
│  • generateInsights(article: Article)                          │
│  • generateAllAIFeatures(article: Article)                     │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                     REPOSITORY LAYER                            │
│                     (AIRepository.kt)                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Business Logic:                                                │
│  ┌─────────────────────────────────────────────────────┐       │
│  │ suspend fun summarizeArticle(content: String)       │       │
│  │   → Builds prompt for summarization                 │       │
│  │   → Calls OpenAI API                                │       │
│  │   → Returns AISummary                               │       │
│  └─────────────────────────────────────────────────────┘       │
│                                                                 │
│  ┌─────────────────────────────────────────────────────┐       │
│  │ suspend fun analyzeSentiment(content: String)       │       │
│  │   → Builds prompt for sentiment analysis            │       │
│  │   → Calls OpenAI API                                │       │
│  │   → Parses sentiment response                       │       │
│  │   → Returns SentimentAnalysis                       │       │
│  └─────────────────────────────────────────────────────┘       │
│                                                                 │
│  ┌─────────────────────────────────────────────────────┐       │
│  │ suspend fun generateInsights(content: String)       │       │
│  │   → Builds prompt for insights extraction           │       │
│  │   → Calls OpenAI API                                │       │
│  │   → Parses insights list                            │       │
│  │   → Returns ArticleInsights                         │       │
│  └─────────────────────────────────────────────────────┘       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                      NETWORK LAYER                              │
│                   (OpenAIService.kt)                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Retrofit Interface:                                            │
│  ┌─────────────────────────────────────────────────────┐       │
│  │ @POST("v1/chat/completions")                        │       │
│  │ suspend fun generateCompletion(                     │       │
│  │     @Body request: ChatCompletionRequest            │       │
│  │ ): ChatCompletionResponse                           │       │
│  └─────────────────────────────────────────────────────┘       │
│                                                                 │
│  Data Models:                                                   │
│  • ChatCompletionRequest                                        │
│  • ChatCompletionResponse                                       │
│  • Message                                                      │
│  • Choice                                                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                   DEPENDENCY INJECTION                          │
│                      (Module.kt)                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  @Provides OpenAI API Key                                       │
│  @Provides OkHttpClient (with Bearer token interceptor)        │
│  @Provides OpenAI Retrofit instance                            │
│  @Provides OpenAIService                                        │
│  @Provides AIRepository                                         │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                      EXTERNAL API                               │
│                  OpenAI GPT-3.5-turbo                           │
│              https://api.openai.com/                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Endpoint: POST /v1/chat/completions                           │
│                                                                 │
│  Request:                                                       │
│  {                                                              │
│    "model": "gpt-3.5-turbo",                                   │
│    "messages": [                                                │
│      {"role": "system", "content": "..."},                     │
│      {"role": "user", "content": "..."}                        │
│    ],                                                           │
│    "max_tokens": 150,                                           │
│    "temperature": 0.7                                           │
│  }                                                              │
│                                                                 │
│  Response:                                                      │
│  {                                                              │
│    "choices": [{                                                │
│      "message": {                                               │
│        "content": "AI-generated response..."                   │
│      }                                                          │
│    }]                                                           │
│  }                                                              │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## Data Flow Diagram

```
User Action
    ↓
[Click "Generate AI Analysis" Button]
    ↓
AIInsightsCard calls → viewModel.generateAllAIFeatures(article)
    ↓
NewsViewModel:
    ├─→ generateAISummary(article)
    │       ↓
    │   aiRepository.summarizeArticle(content)
    │       ↓
    │   OpenAI API Request (Summarization)
    │       ↓
    │   _aiSummary.value = result
    │
    ├─→ analyzeSentiment(article)
    │       ↓
    │   aiRepository.analyzeSentiment(content)
    │       ↓
    │   OpenAI API Request (Sentiment)
    │       ↓
    │   _sentimentAnalysis.value = result
    │
    └─→ generateInsights(article)
            ↓
        aiRepository.generateInsights(content)
            ↓
        OpenAI API Request (Insights)
            ↓
        _articleInsights.value = result
            ↓
        ↓
UI Recomposes with AI Results
    ↓
User sees:
    • AI Summary
    • Sentiment Analysis
    • Key Insights
```

---

## Component Interaction

```
┌────────────────┐
│   Article      │
│   (News Data)  │
└────────┬───────┘
         │
         ↓
┌────────────────────────────────────────┐
│         NewsViewModel                  │
│  ┌──────────────────────────────────┐  │
│  │  AI State Management             │  │
│  │  • aiSummary                     │  │
│  │  • sentimentAnalysis             │  │
│  │  • articleInsights               │  │
│  │  • isAILoading                   │  │
│  └──────────────────────────────────┘  │
│                                        │
│  ┌──────────────────────────────────┐  │
│  │  AI Functions                    │  │
│  │  • generateAISummary()           │  │
│  │  • analyzeSentiment()            │  │
│  │  • generateInsights()            │  │
│  └──────────────────────────────────┘  │
└────────────────┬───────────────────────┘
                 │
                 ↓
┌────────────────────────────────────────┐
│         AIRepository                   │
│  ┌──────────────────────────────────┐  │
│  │  Prompt Engineering              │  │
│  │  • Build context for AI          │  │
│  │  • Format requests               │  │
│  │  • Parse responses               │  │
│  └──────────────────────────────────┘  │
└────────────────┬───────────────────────┘
                 │
                 ↓
┌────────────────────────────────────────┐
│         OpenAIService                  │
│  ┌──────────────────────────────────┐  │
│  │  Retrofit HTTP Client            │  │
│  │  • POST to OpenAI API            │  │
│  │  • Handle authentication         │  │
│  │  • Serialize/Deserialize         │  │
│  └──────────────────────────────────┘  │
└────────────────┬───────────────────────┘
                 │
                 ↓
┌────────────────────────────────────────┐
│      OpenAI GPT-3.5-turbo API          │
│                                        │
│  🤖 AI Processing:                     │
│  • Natural Language Understanding      │
│  • Text Summarization                  │
│  • Sentiment Analysis                  │
│  • Information Extraction              │
└────────────────────────────────────────┘
```

---

## Three AI Features in Detail

### 1. Summarization Flow
```
Article Content
    ↓
"Summarize this article in 2-3 sentences..."
    ↓
OpenAI GPT-3.5-turbo
    ↓
"This article discusses X. Key points include Y. The impact is Z."
    ↓
Display in UI
```

### 2. Sentiment Analysis Flow
```
Article Content
    ↓
"Analyze the sentiment: Positive/Negative/Neutral..."
    ↓
OpenAI GPT-3.5-turbo
    ↓
"Sentiment: Positive
 Confidence: High
 Explanation: The article uses optimistic language..."
    ↓
Color-coded display (Green/Red/Orange)
```

### 3. Insights Extraction Flow
```
Article Content
    ↓
"Extract 3-5 key insights as bullet points..."
    ↓
OpenAI GPT-3.5-turbo
    ↓
"1. First key takeaway
 2. Second important point
 3. Third actionable insight"
    ↓
Numbered list in UI
```

---

## Error Handling Flow

```
User triggers AI feature
    ↓
Try {
    API Request to OpenAI
    ↓
    Success? → Display results
}
Catch {
    Network Error? → Show "Network error" message
    API Error? → Show "API error" message
    Timeout? → Show "Request timeout" message
}
Finally {
    Set isLoading = false
}
```

---

## State Management

```
Initial State:
├─ aiSummary = null
├─ sentimentAnalysis = null
├─ articleInsights = null
└─ isAILoading = false

User clicks "Generate":
├─ isAILoading = true
└─ Show loading indicator

API responds:
├─ aiSummary = AISummary(...)
├─ sentimentAnalysis = SentimentAnalysis(...)
├─ articleInsights = ArticleInsights(...)
└─ isAILoading = false

UI recomposes:
└─ Display all AI results
```

---

This architecture demonstrates clean separation of concerns, proper dependency injection, and meaningful integration of Generative AI into a mobile application.
