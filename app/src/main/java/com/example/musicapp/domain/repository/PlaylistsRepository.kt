package com.example.musicapp.domain.repository

import com.example.musicapp.data.datasource.dto.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylist(id: Long): Flow<Playlist?>
    fun getAllPlaylists(): Flow<List<Playlist>>
    suspend fun addNewPlaylist(name: String, description: String)
    suspend fun deletePlaylistById(id: Long)

    suspend fun insertSongToPlaylist(trackId: Long, playlistId: Long)

    suspend fun deleteSongFromPlaylist(trackId: Long, playlistId: Long)

    suspend fun updatePlaylist(newPlaylist: Playlist)
}