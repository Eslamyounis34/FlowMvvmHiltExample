package com.example.flowmvvmexample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flowmvvmexample.data.ApiService
import com.example.flowmvvmexample.models.RickAndMortyItem

class RickAndMortyRemoteSource(
    private val apiService: ApiService
) : PagingSource<Int, RickAndMortyItem>() {
    override fun getRefreshKey(state: PagingState<Int, RickAndMortyItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickAndMortyItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getAllCharacters(currentPage)
            val responseData = mutableListOf<RickAndMortyItem>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}