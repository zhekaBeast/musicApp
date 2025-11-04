package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.remote.dto.Playlist
import com.example.musicapp.data.datasource.remote.mock.DatabaseMock
import com.example.musicapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
) : PlaylistsRepository {
    private val db = DatabaseMock()
    override fun getPlaylist(id: Long): Flow<Playlist?> {
        return db.getPlaylist(id)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return db.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        db.addNewPlaylist(name, description)
    }

    override suspend fun deletePlaylistById(id: Long) {
        db.deletePlaylistById(id)
    }

}