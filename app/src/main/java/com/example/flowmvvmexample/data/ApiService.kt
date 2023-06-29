package com.example.flowmvvmexample.data

import com.example.flowmvvmexample.models.RickResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/character")
    suspend fun getCharacters():Response<RickResponse>
}