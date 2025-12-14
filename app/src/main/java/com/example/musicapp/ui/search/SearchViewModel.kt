package com.example.musicapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.preferences.SearchHistoryPreferences
import com.example.musicapp.domain.models.TracksSearchRequest
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryPreferences: SearchHistoryPreferences
) : ViewModel() {

    //...
    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial())
    val allTracksScreenState = _allTracksScreenState.asStateFlow()
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        viewModelScope.launch {
            searchHistoryPreferences.getEntries().collect { history ->
                _searchHistory.value = history
            }
        }
    }

    fun fetchData(expression: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.update { SearchState.Loading }
                val list =
                    tracksRepository.searchTracks(TracksSearchRequest(expression = expression))
                if (expression.isNotBlank() && list.isNotEmpty()) {
                    searchHistoryPreferences.addEntry(expression)
                }
                _allTracksScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: IOException) {
                _allTracksScreenState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }

    fun resetState() {
        _allTracksScreenState.update { SearchState.Initial(history = _searchHistory.value) }
    }
}