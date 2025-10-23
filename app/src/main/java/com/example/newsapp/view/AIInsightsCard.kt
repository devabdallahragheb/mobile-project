package com.example.newsapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.AISummary
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticleInsights
import com.example.newsapp.model.SentimentAnalysis
import com.example.newsapp.viewmodel.NewsViewModel

/**
 * AI Insights Card - Displays Generative AI features
 * Shows AI-generated summary, sentiment analysis, and key insights
 */
@Composable
fun AIInsightsCard(
    article: Article,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val aiSummary by viewModel.aiSummary
    val sentimentAnalysis by viewModel.sentimentAnalysis
    val articleInsights by viewModel.articleInsights
    val isLoading by viewModel.isAILoading
    
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Header with AI icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Features",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AI Insights",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Hide" else "Show")
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    // Generate AI Features Button
                    if (aiSummary == null && sentimentAnalysis == null && articleInsights == null && !isLoading) {
                        Button(
                            onClick = { viewModel.generateAllAIFeatures(article) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate AI Analysis")
                        }
                    }

                    // Loading indicator
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Analyzing with AI...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // AI Summary
                    aiSummary?.let { summary ->
                        if (summary.success) {
                            AIFeatureSection(
                                icon = Icons.Default.Summarize,
                                title = "AI Summary",
                                content = summary.summary,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        } else {
                            ErrorSection(summary.error ?: "Failed to generate summary")
                        }
                    }

                    // Sentiment Analysis
                    sentimentAnalysis?.let { sentiment ->
                        if (sentiment.success) {
                            SentimentSection(sentiment)
                        } else {
                            ErrorSection(sentiment.explanation)
                        }
                    }

                    // Article Insights
                    articleInsights?.let { insights ->
                        if (insights.success && insights.insights.isNotEmpty()) {
                            InsightsSection(insights)
                        } else if (!insights.success) {
                            ErrorSection(insights.error ?: "Failed to generate insights")
                        }
                    }

                    // Clear button
                    if (aiSummary != null || sentimentAnalysis != null || articleInsights != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = { viewModel.clearAIAnalysis() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Clear Analysis")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AIFeatureSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String,
    color: Color
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 28.dp)
        )
    }
}

@Composable
private fun SentimentSection(sentiment: SentimentAnalysis) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = getSentimentColor(sentiment.sentiment),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sentiment Analysis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.padding(start = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = getSentimentColor(sentiment.sentiment).copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = sentiment.sentiment,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = getSentimentColor(sentiment.sentiment),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "• ${sentiment.confidence} confidence",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = sentiment.explanation,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 28.dp, top = 4.dp)
        )
    }
}

@Composable
private fun InsightsSection(insights: ArticleInsights) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Insights,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Key Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        insights.insights.forEachIndexed { index, insight ->
            Row(
                modifier = Modifier
                    .padding(start = 28.dp, bottom = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${index + 1}.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = insight,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ErrorSection(error: String) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun getSentimentColor(sentiment: String): Color {
    return when (sentiment.lowercase()) {
        "positive" -> Color(0xFF4CAF50)
        "negative" -> Color(0xFFF44336)
        "neutral" -> Color(0xFFFF9800)
        else -> MaterialTheme.colorScheme.onSurface
    }
}
