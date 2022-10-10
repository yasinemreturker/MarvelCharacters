package com.example.marvelcharacters.api

import com.example.marvelcharacters.data.model.CharacterSpotlight
import com.example.marvelcharacters.data.model.MarvelCharacter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0,
        @Query("nameStartsWith") nameStartsWith: String? = null
    ): ResponseDTO<MarvelCharacter>

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getComics(@Path("characterId") characterId: Int): ResponseDTO<CharacterSpotlight>

    @GET("v1/public/characters/{characterId}/events")
    suspend fun getEvents(@Path("characterId") characterId: Int): ResponseDTO<CharacterSpotlight>

    @GET("v1/public/characters/{characterId}/series")
    suspend fun getSeries(@Path("characterId") characterId: Int): ResponseDTO<CharacterSpotlight>

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getStories(@Path("characterId") characterId: Int): ResponseDTO<CharacterSpotlight>

    companion object {
        /**
         * Marvel API base URL.
         */
        private const val BASE_URL = "https://gateway.marvel.com/"

        /**
         * Query keys that appended to request URL.
         */
        private const val TIMESTAMP_QUERY_KEY = "ts"
        private const val API_KEY_QUERY_KEY = "developer.marvel.com"
        private const val HASH_QUERY_KEY = "hash"

        /**
         * Marvel API public and private keys.
         * They are secured in properties of gradle file.
         */
        //private const val MARVEL_API_KEY = BuildConfig.MARVEL_API_KEY
        private const val MARVEL_API_KEY = "8977b361d769dc843c213f237a2e085d"
        //private const val MARVEL_PRIVATE_API_KEY = BuildConfig.MARVEL_PRIVATE_API_KEY
        private const val MARVEL_PRIVATE_API_KEY = "6e9bfa5b920c05170695b57e38dbd5af4f4fdbbf"

        /**
         * Initiate Retrofit with our service [MarvelService] to request JSON data.
         * [Moshi] to parse JSON objects to Kotlin classes.
         */
        fun create(): MarvelService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val okHttpClient = createOkHttpClient()

            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
                .create(MarvelService::class.java)
        }

        /**
         * Called to intercept request URL to append the required query parameters.
         *
         * @return [OkHttpClient].
         */
        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val currentTime = System.currentTimeMillis()

                    val request = chain.request().newBuilder()
                    val originalUrl = chain.request().url()
                    val newUrl = originalUrl.newBuilder()
                        .addQueryParameter(TIMESTAMP_QUERY_KEY, currentTime.toString())
                        .addQueryParameter(API_KEY_QUERY_KEY, MARVEL_API_KEY)
                        .addQueryParameter(
                            HASH_QUERY_KEY,
                            createHash("$currentTime$MARVEL_PRIVATE_API_KEY$MARVEL_API_KEY")
                        )
                        .build()

                    request.url(newUrl)
                    return@addInterceptor chain.proceed(request.build())
                }
                .build()
        }

        /**
         * Generate a hash value of the input message.
         *
         * @param input input message to be hashed.
         *
         * @return [String] hash value.
         */
        private fun createHash(input: String): String {
            val md5 = MessageDigest.getInstance("MD5")
            return BigInteger(1, md5.digest(input.toByteArray()))
                .toString(16).padStart(32, '0')
        }
    }

}