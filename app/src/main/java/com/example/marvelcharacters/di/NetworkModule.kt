package com.example.marvelcharacters.di

import com.example.marvelcharacters.api.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideMarvelService(): MarvelService {
        return MarvelService.create()
    }
}