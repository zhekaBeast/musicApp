package com.example.musicapp.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.example.musicapp.data.datasource.database.entity.PlaylistEntity
import com.example.musicapp.data.datasource.database.entity.PlaylistTrackCrossRef
import com.example.musicapp.data.datasource.database.entity.TrackEntity
import com.example.musicapp.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

data class PlaylistWithTracks(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = TrackEntity::class,
        associateBy = Junction(
            value = PlaylistTrackCrossRef::class,
            parentColumn = "playlistId",
            entityColumn = "trackId"
        )
    )
    val tracks: List<TrackEntity>


){
    fun toDto(): Playlist {
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

@Dao
interface PlaylistsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: Long)

    @Query("SELECT * FROM playlists WHERE id = :id LIMIT 1")
    fun getPlaylistById(id: Long): Flow<PlaylistEntity?>

    @Query("SELECT * FROM playlists ORDER BY id DESC")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(crossRef: PlaylistTrackCrossRef)

    @Query(
        "DELETE FROM playlist_track_cross_ref " +
            "WHERE playlistId = :playlistId AND trackId = :trackId"
    )
    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :id LIMIT 1")
    fun getPlaylistWithTracks(id: Long): Flow<PlaylistWithTracks?>

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistsWithTracks(): Flow<List<PlaylistWithTracks>>
}

