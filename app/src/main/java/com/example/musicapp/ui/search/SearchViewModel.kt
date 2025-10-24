package com.example.musicapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    //...
    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Loading)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData(expression: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.update { SearchState.Loading }
                val list = tracksRepository.SearchTracks(expression)
                _allTracksScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: IOException) {
                _allTracksScreenState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }
}