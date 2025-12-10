package com.example.musicapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteTracks().collect { tracks ->
                _state.update {
                    FavoritesState.Success(tracks)
                }
            }
        }
    }
}

sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Success(val tracks: List<Track>) : FavoritesState()
    data class Error(val message: String) : FavoritesState()
}