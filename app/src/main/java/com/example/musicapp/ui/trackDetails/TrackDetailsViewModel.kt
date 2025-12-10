package com.example.musicapp.ui.trackDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TrackDetailsViewModel(
    private val tracksRepository: TracksRepository,
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private val _state = MutableStateFlow<TrackDetailsState>(TrackDetailsState.Initial)
    val state = _state.asStateFlow()

    fun loadTrack(trackId: Long) {
        viewModelScope.launch {
            try {
                _state.update { TrackDetailsState.Loading }
                val track = tracksRepository.getTrackById(trackId)

                if (track == null) {
                    _state.update { TrackDetailsState.NotFound }
                    return@launch
                }

                _state.update {
                    TrackDetailsState.Success(
                        track = track,
                        playlists = PlaylistsState.Loading
                    )
                }

                loadPlaylists()
            } catch (e: IOException) {
                _state.update { TrackDetailsState.Error(e.message.toString()) }
            }
        }
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is TrackDetailsState.Success) {
                try {
                    val playlists = playlistsRepository.getAllPlaylists()

                    _state.update {
                        if (it is TrackDetailsState.Success) {
                            it.copy(playlists = PlaylistsState.Loaded(playlists))
                        } else it
                    }
                } catch (e: Exception) {
                    _state.update { TrackDetailsState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun toggleFavorite(){
        val currentState = _state.value
        if (currentState is TrackDetailsState.Success) {
            viewModelScope.launch {
                try {
                    val currentPlaylists = currentState.playlists
                    _state.update { TrackDetailsState.Loading }
                    val updatedTrack = tracksRepository.updateFavoriteStatus(
                        currentState.track!!.id,
                        !currentState.track.favorite
                    )
                    if(updatedTrack==null) {
                        _state.update { TrackDetailsState.NotFound }
                    } else{
                        _state.update { TrackDetailsState.Success(updatedTrack, currentPlaylists) }
                    }
                } catch (e: IOException) {
                    _state.update { TrackDetailsState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun addTrackToPlaylist(playlistId: Long) {
        val currentState = _state.value
        if (currentState is TrackDetailsState.Success) {
            viewModelScope.launch {
                try {
                    playlistsRepository.insertSongToPlaylist(
                        playlistId = playlistId,
                        trackId = currentState.track.id
                    )
                    loadPlaylists()

                } catch (e: Exception) {
                    _state.update { TrackDetailsState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: Long) {
        val currentState = _state.value
        if (currentState is TrackDetailsState.Success) {
            viewModelScope.launch {
                try {
                    playlistsRepository.deleteSongFromPlaylist(
                        playlistId = playlistId,
                        trackId = currentState.track.id
                    )
                    loadPlaylists()
                } catch (e: Exception) {
                    _state.update { TrackDetailsState.Error(e.message.toString()) }
                }
            }
        }
    }
}

sealed class TrackDetailsState(){
    object Initial: TrackDetailsState()
    object Loading: TrackDetailsState()

    object NotFound : TrackDetailsState()
    data class Success(val track: Track, val playlists: PlaylistsState) :
        TrackDetailsState()
    data class Error(val error: String): TrackDetailsState()
}

sealed class PlaylistsState {
    object Loading : PlaylistsState()
    data class Loaded(val playlists: Flow<List<Playlist>>) : PlaylistsState()
    data class Error(val message: String) : PlaylistsState()
}