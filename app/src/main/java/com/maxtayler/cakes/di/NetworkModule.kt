package com.maxtayler.cakes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                val json = Json { ignoreUnknownKeys = true }
                json(json = json, contentType = ContentType.Text.Plain)
            }
        }
    }
}