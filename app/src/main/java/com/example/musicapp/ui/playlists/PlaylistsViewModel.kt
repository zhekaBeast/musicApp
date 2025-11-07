package com.example.musicapp.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository :TracksRepository
) : ViewModel() {
    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()
    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()



    fun insertSongToPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.insertSongToPlaylist(track.id, playlist.id)
        }
    }

    fun deleteSongFromPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.deleteSongFromPlaylist(track.id, playlist.id)
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
        playlistsRepository.deletePlaylistById(playlist.id)
            }
    }
}