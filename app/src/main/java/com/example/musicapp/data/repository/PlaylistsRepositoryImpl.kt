package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.data.datasource.mock.DatabaseMock
import com.example.musicapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val db: DatabaseMock
) : PlaylistsRepository {
    override fun getPlaylist(id: Long): Flow<Playlist?> {
        return db.getPlaylist(id)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return db.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String, imageUri: String?) {
        db.addNewPlaylist(name, description)
    }

    override suspend fun deletePlaylistById(id: Long) {
        db.deletePlaylistById(id)
    }

    override suspend fun insertSongToPlaylist(
        trackId: Long,
        playlistId: Long
    ) {
        db.insertSongToPlaylist(trackId, playlistId)
    }

    override suspend fun deleteSongFromPlaylist(trackId: Long, playlistId: Long) {
        db.deleteSongFromPlaylist(trackId, playlistId)
    }

    override suspend fun updatePlaylist(newPlaylist: Playlist) {
        db.updatePlaylist(newPlaylist)
    }

}