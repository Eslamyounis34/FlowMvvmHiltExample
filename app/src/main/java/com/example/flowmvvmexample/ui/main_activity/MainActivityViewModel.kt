package com.example.flowmvvmexample.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.flowmvvmexample.data.ApiService
import com.example.flowmvvmexample.models.RickAndMortyItem
import com.example.flowmvvmexample.paging.RickAndMortyRemoteSource
import com.example.flowmvvmexample.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val apiService: ApiService
) :
    ViewModel() {

    // TODO: following code for passing paging data list
    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickAndMortyRemoteSource(apiService)

    }.flow.cachedIn(viewModelScope)
}