package com.example.flowmvvmexample.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.flowmvvmexample.data.ApiService
import com.example.flowmvvmexample.models.RickAndMortyItem
import com.example.flowmvvmexample.paging.RickAndMortyRemoteSource
import com.example.flowmvvmexample.repository.RickAndMortyRepository
import com.example.flowmvvmexample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) :
    ViewModel() {

    private val _isLoading = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val isLoading: StateFlow<Resource<Boolean>>
        get() = _isLoading

    // TODO: following code for passing paging data list
    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickAndMortyRemoteSource(repository,_isLoading)

    }.flow.cachedIn(viewModelScope)
}