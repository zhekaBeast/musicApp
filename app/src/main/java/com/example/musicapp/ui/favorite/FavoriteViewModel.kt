package com.example.musicapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {
    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            tracksRepository.getFavoriteTracks().collect { tracks ->
                _state.update {
                    FavoritesState.Success(tracks)
                }
            }
        }
    }

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch {
            try {
                tracksRepository.updateFavoriteStatus(track.id, false)
            } catch (e: Exception) {
                _state.update { FavoritesState.Error(e.message ?: "Ошибка удаления из избранного") }
            }
        }
    }
}

sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Success(val tracks: List<Track>) : FavoritesState()
    data class Error(val message: String) : FavoritesState()
}