package com.example.newsapp.model

import kotlinx.serialization.Serializable

/**
 * AI Repository for managing Generative AI operations
 * Handles article summarization and sentiment analysis
 */
class AIRepository(
    private val openAIService: OpenAIService
) {
    
    /**
     * Generate a concise summary of an article using OpenAI GPT
     * @param articleContent The full article content to summarize
     * @return AI-generated summary
     */
    suspend fun summarizeArticle(articleContent: String): AISummary {
        return try {
            val prompt = """
                Summarize the following news article in 2-3 concise sentences. 
                Focus on the key facts and main points:
                
                $articleContent
            """.trimIndent()
            
            val request = ChatCompletionRequest(
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(role = "system", content = "You are a helpful assistant that summarizes news articles concisely."),
                    Message(role = "user", content = prompt)
                ),
                maxTokens = 150,
                temperature = 0.5
            )
            
            val response = openAIService.generateCompletion(request)
            val summary = response.choices.firstOrNull()?.message?.content ?: "Summary not available"
            
            AISummary(
                summary = summary.trim(),
                success = true,
                error = null
            )
        } catch (e: Exception) {
            AISummary(
                summary = "",
                success = false,
                error = "Failed to generate summary: ${e.message}"
            )
        }
    }
    
    /**
     * Analyze the sentiment of an article using OpenAI GPT
     * @param articleContent The article content to analyze
     * @return Sentiment analysis result
     */
    suspend fun analyzeSentiment(articleContent: String): SentimentAnalysis {
        return try {
            val prompt = """
                Analyze the sentiment and tone of this news article. 
                Provide: 1) Overall sentiment (Positive/Negative/Neutral), 
                2) Confidence level (High/Medium/Low), 
                3) Brief explanation in one sentence.
                
                Article: $articleContent
                
                Format your response as:
                Sentiment: [sentiment]
                Confidence: [confidence]
                Explanation: [explanation]
            """.trimIndent()
            
            val request = ChatCompletionRequest(
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(role = "system", content = "You are an expert at analyzing sentiment in news articles."),
                    Message(role = "user", content = prompt)
                ),
                maxTokens = 100,
                temperature = 0.3
            )
            
            val response = openAIService.generateCompletion(request)
            val analysis = response.choices.firstOrNull()?.message?.content ?: ""
            
            parseSentimentResponse(analysis)
        } catch (e: Exception) {
            SentimentAnalysis(
                sentiment = "Unknown",
                confidence = "Low",
                explanation = "Failed to analyze sentiment: ${e.message}",
                success = false
            )
        }
    }
    
    /**
     * Generate key insights from an article
     * @param articleContent The article content
     * @return List of key insights
     */
    suspend fun generateInsights(articleContent: String): ArticleInsights {
        return try {
            val prompt = """
                Extract 3-5 key insights or takeaways from this news article.
                List them as bullet points, each being one clear, actionable insight.
                
                Article: $articleContent
            """.trimIndent()
            
            val request = ChatCompletionRequest(
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(role = "system", content = "You are an expert at extracting key insights from news articles."),
                    Message(role = "user", content = prompt)
                ),
                maxTokens = 200,
                temperature = 0.6
            )
            
            val response = openAIService.generateCompletion(request)
            val insights = response.choices.firstOrNull()?.message?.content ?: ""
            
            ArticleInsights(
                insights = parseInsights(insights),
                success = true,
                error = null
            )
        } catch (e: Exception) {
            ArticleInsights(
                insights = emptyList(),
                success = false,
                error = "Failed to generate insights: ${e.message}"
            )
        }
    }
    
    private fun parseSentimentResponse(response: String): SentimentAnalysis {
        val lines = response.lines()
        var sentiment = "Neutral"
        var confidence = "Medium"
        var explanation = ""
        
        lines.forEach { line ->
            when {
                line.startsWith("Sentiment:", ignoreCase = true) -> 
                    sentiment = line.substringAfter(":").trim()
                line.startsWith("Confidence:", ignoreCase = true) -> 
                    confidence = line.substringAfter(":").trim()
                line.startsWith("Explanation:", ignoreCase = true) -> 
                    explanation = line.substringAfter(":").trim()
            }
        }
        
        return SentimentAnalysis(
            sentiment = sentiment,
            confidence = confidence,
            explanation = explanation.ifEmpty { response },
            success = true
        )
    }
    
    private fun parseInsights(response: String): List<String> {
        return response.lines()
            .filter { it.trim().isNotEmpty() }
            .map { it.trim().removePrefix("-").removePrefix("•").removePrefix("*").trim() }
            .filter { it.isNotEmpty() }
    }
}

@Serializable
data class AISummary(
    val summary: String,
    val success: Boolean,
    val error: String?
)

@Serializable
data class SentimentAnalysis(
    val sentiment: String,
    val confidence: String,
    val explanation: String,
    val success: Boolean
)

@Serializable
data class ArticleInsights(
    val insights: List<String>,
    val success: Boolean,
    val error: String?
)
