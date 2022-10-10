package com.example.marvelcharacters.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelCharacter(
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val id: Int,
) : Parcelable