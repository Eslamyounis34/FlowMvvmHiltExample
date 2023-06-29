package com.example.flowmvvmexample.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowmvvmexample.models.RickAndMortyItem
import com.example.flowmvvmexample.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val rickRepo: RickAndMortyRepository) :
    ViewModel() {

    val characters: MutableStateFlow<List<RickAndMortyItem>> = MutableStateFlow(emptyList())

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            rickRepo.getCharacters().collect() {
                characters.value = it.body()!!.results
            }
        }
    }
}