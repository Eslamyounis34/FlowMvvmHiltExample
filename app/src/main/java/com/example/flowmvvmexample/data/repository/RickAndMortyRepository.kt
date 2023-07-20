package com.example.flowmvvmexample.data.repository

import com.example.flowmvvmexample.data.models.RickAndMortyItem
import com.example.flowmvvmexample.data.remote.ApiService
import com.example.flowmvvmexample.data.models.RickResponse
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

    suspend fun getAllCharacters(page: Int): Response<RickResponse> {
        val response = apiService.getAllCharacters(page)
        return response
    }

    suspend fun getSingleCharacter(id: Int): Flow<RickAndMortyItem> {
        return flow {
            emit(
                apiService.getSingleCharacter(id)
            )
        }.flowOn(Dispatchers.IO)
    }
}