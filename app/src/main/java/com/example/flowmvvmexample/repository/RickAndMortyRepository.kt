package com.example.flowmvvmexample.repository

import com.example.flowmvvmexample.data.ApiService
import com.example.flowmvvmexample.models.RickResponse
import com.example.flowmvvmexample.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(private val apiService: ApiService) {

    fun getCharacters(): Flow<Response<RickResponse>> = flow {
        val response = apiService.getCharacters()
        emit(response)
    }.flowOn(Dispatchers.IO)

   suspend fun getAllCharacters(page:Int): Response<RickResponse> {
        val response = apiService.getAllCharacters(page)
       return  response
    }

}