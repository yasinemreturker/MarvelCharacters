package com.example.marvelcharacters.api

data class ResponseDTO<T>(
    val data: DataDTO<T>
)
