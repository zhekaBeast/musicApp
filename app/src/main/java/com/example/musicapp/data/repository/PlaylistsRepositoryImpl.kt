package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.database.dao.PlaylistsDao
import com.example.musicapp.data.datasource.database.entity.PlaylistEntity
import com.example.musicapp.data.datasource.database.entity.PlaylistTrackCrossRef
import com.example.musicapp.domain.models.Playlist
import com.example.musicapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val playlistsDao: PlaylistsDao
) : PlaylistsRepository {

    override fun getPlaylist(id: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistWithTracks(id)
            .map { playlistWithTracks ->
                playlistWithTracks?.toDto()
            }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getAllPlaylistsWithTracks()
            .map { list -> list.map { it.toDto() } }
    }

    override suspend fun addNewPlaylist(name: String, description: String, imageUri: String?) {
        val entity = PlaylistEntity(
            name = name,
            description = description,
            coverImageUri = imageUri
        )
        playlistsDao.insertPlaylist(entity)
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylistById(id)
        // Optionally: clean up cross-refs in a separate DAO method if needed
    }

    override suspend fun insertSongToPlaylist(
        trackId: Long,
        playlistId: Long
    ) {
        playlistsDao.insertTrackToPlaylist(
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = trackId
            )
        )
    }

    override suspend fun deleteSongFromPlaylist(trackId: Long, playlistId: Long) {
        playlistsDao.deleteTrackFromPlaylist(playlistId, trackId)
    }


}