package com.example.marvelcharacters.presentation.viewmodel

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.marvelcharacters.data.model.MarvelCharacter
import com.example.marvelcharacters.data.repo.MarvelRepository
import com.example.marvelcharacters.util.SpotlightType
import com.example.marvelcharacters.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val marvelRepository: MarvelRepository,
    private val state: SavedStateHandle
) :
    ViewModel(), Parcelable {

    val comics = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(
                Result.Success(
                    marvelRepository.getMarvelCharacterSpotlights(
                        state.get<MarvelCharacter>("marvelCharacter")?.id!!,
                        SpotlightType.COMICS
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    val events = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(
                Result.Success(
                    marvelRepository.getMarvelCharacterSpotlights(
                        state.get<MarvelCharacter>("marvelCharacter")?.id!!,
                        SpotlightType.EVENTS
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    val series = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(
                Result.Success(
                    marvelRepository.getMarvelCharacterSpotlights(
                        state.get<MarvelCharacter>("marvelCharacter")?.id!!,
                        SpotlightType.SERIES
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    val stories = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(
                Result.Success(
                    marvelRepository.getMarvelCharacterSpotlights(
                        state.get<MarvelCharacter>("marvelCharacter")?.id!!,
                        SpotlightType.STORIES
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    constructor(parcel: Parcel) : this(
        TODO("marvelRepository"),
        TODO("state")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailsViewModel> {
        override fun createFromParcel(parcel: Parcel): DetailsViewModel {
            return DetailsViewModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailsViewModel?> {
            return arrayOfNulls(size)
        }
    }
}