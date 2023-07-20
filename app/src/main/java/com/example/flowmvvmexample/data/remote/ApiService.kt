package com.example.flowmvvmexample.data.remote

import com.example.flowmvvmexample.data.models.RickAndMortyItem
import com.example.flowmvvmexample.data.models.RickResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun getCharacters(): Response<RickResponse>

    //TODO this api for paging
    @GET("api/character")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<RickResponse>

    @GET("api/character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: Int): RickAndMortyItem
}
