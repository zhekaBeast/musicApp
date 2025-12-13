package com.example.musicapp.ui.search

import com.example.musicapp.domain.models.Track

sealed class SearchState {
    data class Initial(val history: List<String> = emptyList()) : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
}