package com.example.flowmvvmexample.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.flowmvvmexample.common.utils.SingleLiveEvent
import com.example.flowmvvmexample.paging.RickAndMortyRemoteSource
import com.example.flowmvvmexample.data.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) :
    ViewModel() {

//    private val _isLoading = SingleLiveEvent<Boolean>()
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    init {
//        _isLoading.value = true
//    }

    // TODO: following code for passing paging data list
    val listData = Pager(PagingConfig(pageSize = 1)) {
        RickAndMortyRemoteSource(repository)

    }.flow.cachedIn(viewModelScope)
}