package com.example.musicapp.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.Playlist
import com.example.musicapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private val _state = MutableStateFlow<PlaylistsState>(PlaylistsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadPlaylists()
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            try {
                val playlists = playlistsRepository.getAllPlaylists()
                _state.update { PlaylistsState.Success(playlists) }
            } catch (e: Exception) {
                _state.update { PlaylistsState.Error(e.message ?: "Ошибка") }
            }
        }
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch {
            try {
                playlistsRepository.deletePlaylistById(id)
            } catch (e: Exception) {
                _state.update { PlaylistsState.Error(e.message ?: "Ошибка удаления плейлиста") }
            }
        }
    }
}

sealed class PlaylistsState {
    object Loading : PlaylistsState()
    data class Success(val playlists: Flow<List<Playlist>>) : PlaylistsState()
    data class Error(val message: String) : PlaylistsState()
}