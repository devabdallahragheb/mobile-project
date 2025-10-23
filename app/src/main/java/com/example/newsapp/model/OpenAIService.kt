package com.example.newsapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * OpenAI API Service for Generative AI Integration
 * Provides article summarization and sentiment analysis using GPT models
 */
interface OpenAIService {
    
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun generateCompletion(
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}

@Serializable
data class ChatCompletionRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    @SerialName("max_tokens")
    val maxTokens: Int = 150,
    val temperature: Double = 0.7
)

@Serializable
data class Message(
    val role: String, // "system", "user", or "assistant"
    val content: String
)

@Serializable
data class ChatCompletionResponse(
    val id: String,
    val choices: List<Choice>,
    val usage: Usage
)

@Serializable
data class Choice(
    val index: Int,
    val message: Message,
    @SerialName("finish_reason")
    val finishReason: String
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int
)
