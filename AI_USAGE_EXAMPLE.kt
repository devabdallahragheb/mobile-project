package com.example.newsapp.example

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.model.Article
import com.example.newsapp.view.AIInsightsCard
import com.example.newsapp.viewmodel.NewsViewModel

/**
 * EXAMPLE: How to integrate AI features in your Article Detail Screen
 * 
 * This demonstrates the meaningful use of Generative AI (OpenAI GPT-3.5-turbo)
 * in the NewsApp with three key features:
 * 1. Article Summarization
 * 2. Sentiment Analysis  
 * 3. Key Insights Extraction
 */

@Composable
fun ArticleDetailScreenWithAI(
    article: Article,
    viewModel: NewsViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Article Header
        item {
            Text(
                text = article.title ?: "No Title",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ========== AI INSIGHTS CARD ==========
        // This is where the magic happens!
        // The AIInsightsCard provides all three AI features:
        // - Summarization
        // - Sentiment Analysis
        // - Key Insights
        item {
            AIInsightsCard(
                article = article,
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Article Content
        item {
            Text(
                text = article.description ?: "No description available",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = article.content ?: "No content available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * EXAMPLE 2: Manual AI Feature Usage
 * If you want to trigger AI features individually
 */
@Composable
fun ManualAIFeaturesExample(
    article: Article,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val aiSummary by viewModel.aiSummary
    val sentimentAnalysis by viewModel.sentimentAnalysis
    val articleInsights by viewModel.articleInsights
    val isLoading by viewModel.isAILoading

    Column(modifier = Modifier.padding(16.dp)) {
        // Button 1: Generate Summary
        Button(
            onClick = { viewModel.generateAISummary(article) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate AI Summary")
        }

        // Display Summary
        aiSummary?.let { summary ->
            if (summary.success) {
                Card(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = summary.summary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button 2: Analyze Sentiment
        Button(
            onClick = { viewModel.analyzeSentiment(article) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Analyze Sentiment")
        }

        // Display Sentiment
        sentimentAnalysis?.let { sentiment ->
            if (sentiment.success) {
                Card(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Sentiment: ${sentiment.sentiment}")
                        Text("Confidence: ${sentiment.confidence}")
                        Text("Explanation: ${sentiment.explanation}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button 3: Generate Insights
        Button(
            onClick = { viewModel.generateInsights(article) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Key Insights")
        }

        // Display Insights
        articleInsights?.let { insights ->
            if (insights.success) {
                Card(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Key Insights:", style = MaterialTheme.typography.titleMedium)
                        insights.insights.forEachIndexed { index, insight ->
                            Text("${index + 1}. $insight")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button 4: Generate All Features at Once
        Button(
            onClick = { viewModel.generateAllAIFeatures(article) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Generate All AI Features")
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * EXAMPLE 3: Simple Integration in Existing Screen
 * Add this to any existing article screen
 */
@Composable
fun SimpleAIIntegration(article: Article, viewModel: NewsViewModel) {
    // Just add this one component and you get all AI features!
    AIInsightsCard(
        article = article,
        viewModel = viewModel
    )
}

/**
 * CODE SNIPPET: How the AI works behind the scenes
 * 
 * 1. User clicks "Generate AI Analysis"
 * 2. ViewModel calls AIRepository
 * 3. AIRepository sends request to OpenAI API
 * 4. OpenAI GPT-3.5-turbo processes the article
 * 5. Response is parsed and displayed in UI
 * 
 * Example API Request:
 * {
 *   "model": "gpt-3.5-turbo",
 *   "messages": [
 *     {"role": "system", "content": "You are a helpful assistant..."},
 *     {"role": "user", "content": "Summarize this article: ..."}
 *   ],
 *   "max_tokens": 150,
 *   "temperature": 0.7
 * }
 * 
 * Example API Response:
 * {
 *   "choices": [{
 *     "message": {
 *       "content": "This article discusses... Key points include..."
 *     }
 *   }]
 * }
 */

/**
 * TESTING THE AI FEATURES
 * 
 * To test the AI integration:
 * 1. Run the app
 * 2. Navigate to any article
 * 3. Look for the "AI Insights" card
 * 4. Click "Show" to expand
 * 5. Click "Generate AI Analysis"
 * 6. Wait 2-5 seconds for OpenAI to respond
 * 7. See the results:
 *    - AI Summary (2-3 sentences)
 *    - Sentiment Analysis (Positive/Negative/Neutral with explanation)
 *    - Key Insights (3-5 bullet points)
 */
