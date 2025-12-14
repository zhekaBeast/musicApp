package com.example.musicapp.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.Playlist
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PlaylistViewModel(
    private val tracksRepository: TracksRepository,
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private val _playlistWithTracksState =
        MutableStateFlow<PlaylistWithTracksState>(PlaylistWithTracksState.Loading)
    val playlistState = _playlistWithTracksState.asStateFlow()


    fun loadPlaylistWithTracks(playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _playlistWithTracksState.update { PlaylistWithTracksState.Loading }

                val playlist = playlistsRepository.getPlaylist(playlistId).first()

                if (playlist == null) {
                    _playlistWithTracksState.update { PlaylistWithTracksState.NotFound }
                    return@launch
                }

                val tracks = playlist.trackIds.mapNotNull { trackId ->
                    try {
                        tracksRepository.getTrackById(trackId)
                    } catch (e: Exception) {
                        null
                    }
                }

                val playlistWithTracks = PlaylistWithTracks(playlist, tracks)

                _playlistWithTracksState.update {
                    PlaylistWithTracksState.Loaded(playlistWithTracks)
                }

            } catch (e: IOException) {
                _playlistWithTracksState.update { PlaylistWithTracksState.Error(e.message.toString()) }
            } catch (e: Exception) {
                _playlistWithTracksState.update { PlaylistWithTracksState.Error(e.message.toString()) }
            }
        }
    }

    fun deletePlaylist() {
        val currentState = _playlistWithTracksState.value
        if (currentState is PlaylistWithTracksState.Loaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    playlistsRepository.deletePlaylistById(currentState.playlistWithTracks.playlist.id)
                } catch (e: Exception) {
                    _playlistWithTracksState.update { PlaylistWithTracksState.Error(e.message.toString()) }
                }
            }
        }
    }
}


sealed class PlaylistWithTracksState {
    object Loading : PlaylistWithTracksState()
    object NotFound : PlaylistWithTracksState()
    data class Loaded(
        val playlistWithTracks: PlaylistWithTracks
    ) : PlaylistWithTracksState()

    data class Error(val message: String) : PlaylistWithTracksState()
}

data class PlaylistWithTracks(
    val playlist: Playlist,
    val tracks: List<Track>
) {
    val totalDuration: Long
        get() = tracks.sumOf { it.trackTimeMillis } / 60000
}