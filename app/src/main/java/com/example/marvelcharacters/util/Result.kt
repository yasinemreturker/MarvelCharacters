package com.example.marvelcharacters.util

sealed class Result<out R> {
    object Loading : Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
    class Error(val exception: Exception) : Result<Nothing>()

    val dataIfAvailable: R?
        get() = when (this) {
            Loading -> null
            is Error -> null
            is Success -> data
        }
}
