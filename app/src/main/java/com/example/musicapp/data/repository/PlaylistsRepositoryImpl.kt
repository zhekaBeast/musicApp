package com.example.musicapp.data.repository

import com.example.musicapp.data.database.dao.PlaylistsDao
import com.example.musicapp.data.database.entity.PlaylistEntity
import com.example.musicapp.data.database.entity.PlaylistTrackCrossRef
import com.example.musicapp.data.database.dao.PlaylistWithTracks
import com.example.musicapp.data.datasource.dto.Playlist
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

    override suspend fun updatePlaylist(newPlaylist: Playlist) {
        val entity = PlaylistEntity(
            id = newPlaylist.id,
            name = newPlaylist.name,
            description = newPlaylist.description,
            coverImageUri = newPlaylist.coverImageUri
        )
        playlistsDao.updatePlaylist(entity)
    }

    private fun PlaylistWithTracks.toDto(): Playlist {
        val trackIds = tracks.map { it.id }
        return Playlist(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            coverImageUri = playlist.coverImageUri,
            trackIds = trackIds
        )
    }
}