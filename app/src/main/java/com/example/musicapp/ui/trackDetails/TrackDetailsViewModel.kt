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
    private val _trackState = MutableStateFlow<TrackState>(TrackState.Loading)
    private val _playlistsState = MutableStateFlow<PlaylistsState>(PlaylistsState.Loading)
    val trackState = _trackState.asStateFlow()
    val playlistsState = _playlistsState.asStateFlow()
    fun loadTrack(trackId: Long) {
        viewModelScope.launch {
            try {
                val track = tracksRepository.getTrackById(trackId)
                if (track == null) {
                    _trackState.update { TrackState.NotFound }
                    return@launch
                }
                _trackState.update {
                    TrackState.Success(
                        track = track,
                    )
                }
            } catch (e: IOException) {
                _trackState.update { TrackState.Error(e.message.toString()) }
            }
        }
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            try {
                val playlists = playlistsRepository.getAllPlaylists()
                _playlistsState.update { PlaylistsState.Loaded(playlists) }
            } catch (e: Exception) {
                _playlistsState.update { PlaylistsState.Error(e.message.toString()) }
            }
        }
    }

    fun toggleFavorite(){
        val currentState = _trackState.value
        if (currentState is TrackState.Success) {
            viewModelScope.launch {
                try {
                    _trackState.update { TrackState.Loading }
                    val updatedTrack = tracksRepository.updateFavoriteStatus(
                        currentState.track!!.id,
                        !currentState.track.favorite
                    )
                    if(updatedTrack==null) {
                        _trackState.update { TrackState.NotFound }
                    } else{
                        _trackState.update { TrackState.Success(updatedTrack) }
                    }
                } catch (e: IOException) {
                    _trackState.update { TrackState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun addTrackToPlaylist(playlistId: Long) {
        val currentState = _trackState.value
        if (currentState is TrackState.Success) {
            viewModelScope.launch {
                try {
                    playlistsRepository.insertSongToPlaylist(
                        playlistId = playlistId,
                        trackId = currentState.track.id
                    )
                    loadPlaylists()
                } catch (e: Exception) {
                    _trackState.update { TrackState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: Long) {
        val currentState = _trackState.value
        if (currentState is TrackState.Success) {
            viewModelScope.launch {
                try {
                    playlistsRepository.deleteSongFromPlaylist(
                        playlistId = playlistId,
                        trackId = currentState.track.id
                    )
                    loadPlaylists()
                } catch (e: Exception) {
                    _trackState.update { TrackState.Error(e.message.toString()) }
                }
            }
        }
    }
}

sealed class TrackState() {
    object Loading : TrackState()
    object NotFound : TrackState()
    data class Success(val track: Track) :
        TrackState()

    data class Error(val error: String) : TrackState()
}

sealed class PlaylistsState {
    object Loading : PlaylistsState()
    data class Loaded(val playlists: Flow<List<Playlist>>) : PlaylistsState()
    data class Error(val message: String) : PlaylistsState()
}