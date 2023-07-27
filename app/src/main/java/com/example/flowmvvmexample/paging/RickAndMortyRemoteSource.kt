package com.example.flowmvvmexample.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flowmvvmexample.data.models.RickAndMortyItem
import com.example.flowmvvmexample.data.repository.RickAndMortyRepository
import com.example.flowmvvmexample.common.utils.Resource

class RickAndMortyRemoteSource(
    private val repo: RickAndMortyRepository,
    //private val _isLoading: MutableLiveData<Boolean>

) : PagingSource<Int, RickAndMortyItem>() {
    override fun getRefreshKey(state: PagingState<Int, RickAndMortyItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickAndMortyItem> {
        return try {
          //  _isLoading.postValue(true)
            val currentPage = params.key ?: 1
            val response = repo.getAllCharacters(currentPage)
            val responseData = mutableListOf<RickAndMortyItem>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
           // _isLoading.postValue(false)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            Log.e("testError",e.toString())
           // _isLoading.postValue(false)
            LoadResult.Error(e)
        }
    }
}