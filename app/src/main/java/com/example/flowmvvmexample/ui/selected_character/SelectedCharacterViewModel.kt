package com.example.flowmvvmexample.ui.selected_character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowmvvmexample.common.utils.Resource
import com.example.flowmvvmexample.data.models.RickAndMortyItem
import com.example.flowmvvmexample.data.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

import javax.inject.Inject
@HiltViewModel
class SelectedCharacterViewModel @Inject constructor(private val repo: RickAndMortyRepository) :
    ViewModel() {

    private val _characterData = MutableStateFlow<Resource<RickAndMortyItem>>(Resource.Loading())
    val liveCharacterData: StateFlow<Resource<RickAndMortyItem>>
        get() = _characterData

    suspend fun getCharacterData(id: Int) {
        repo.getSingleCharacter(id)
            .map { Resource.Success(it) }
            .onStart { _characterData.value = Resource.Loading() }
            .catch { e -> _characterData.value = Resource.DataError(1) }
            .collect { _characterData.value = it }

    }
}