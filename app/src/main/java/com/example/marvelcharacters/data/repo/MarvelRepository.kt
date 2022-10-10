package com.example.marvelcharacters.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.marvelcharacters.api.MarvelService
import com.example.marvelcharacters.data.datasource.CharactersDataSource
import com.example.marvelcharacters.util.DEFAULT_PAGE_SIZE
import com.example.marvelcharacters.util.SpotlightType
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val marvelService: MarvelService) {

    fun getMarvelCharacters() = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        CharactersDataSource(marvelService)
    }.liveData

    fun searchForMarvelCharacters(query: String) = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        CharactersDataSource(marvelService, query)
    }.flow

    suspend fun getMarvelCharacterSpotlights(
        marvelCharacterId: Int,
        spotlightType: SpotlightType
    ) = when (spotlightType) {
        SpotlightType.COMICS -> marvelService.getComics(marvelCharacterId).data.results
        SpotlightType.EVENTS -> marvelService.getEvents(marvelCharacterId).data.results
        SpotlightType.SERIES -> marvelService.getSeries(marvelCharacterId).data.results
        SpotlightType.STORIES -> marvelService.getStories(marvelCharacterId).data.results
    }
}