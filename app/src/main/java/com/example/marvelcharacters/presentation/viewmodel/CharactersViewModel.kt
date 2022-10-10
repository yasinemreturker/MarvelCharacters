package com.example.marvelcharacters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.repo.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(marvelRepository: MarvelRepository) : ViewModel() {

    val marvelCharacters = marvelRepository.getMarvelCharacters().cachedIn(viewModelScope)
}