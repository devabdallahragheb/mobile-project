package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.FavoriteArticleDao
import com.example.newsapp.data.NewsDatabase
import com.example.newsapp.model.AIRepository
import com.example.newsapp.model.NewsApiService
import com.example.newsapp.model.OpenAIService
import com.example.newsapp.model.RetrofitInstance.API_KEY
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit):NewsApiService{
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKey():String{
        return API_KEY
    }


    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }

    // ========== OpenAI / Generative AI Integration ==========
    
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OpenAIRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NewsRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OpenAIApiKey

    @Provides
    @Singleton
    @OpenAIApiKey
    fun provideOpenAIApiKey(): String {
        // TODO: Store this securely in local.properties or BuildConfig in production
        // Get your API key from: https://platform.openai.com/api-keys
        return "YOUR_OPENAI_API_KEY_HERE"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@OpenAIApiKey openAIApiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $openAIApiKey")
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Singleton
    @OpenAIRetrofit
    fun provideOpenAIRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAIService(@OpenAIRetrofit retrofit: Retrofit): OpenAIService {
        return retrofit.create(OpenAIService::class.java)
    }

    @Provides
    @Singleton
    fun provideAIRepository(openAIService: OpenAIService): AIRepository {
        return AIRepository(openAIService)
    }

    // ========== Room Database for Favorites ==========
    
    @Provides
    @Singleton
    fun provideNewsDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteArticleDao(database: NewsDatabase): FavoriteArticleDao {
        return database.favoriteArticleDao()
    }
}