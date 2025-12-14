package com.example.musicapp.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicapp.data.datasource.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @Query("SELECT * FROM tracks WHERE id = :id LIMIT 1")
    fun getTrackById(id: Long): TrackEntity?

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("UPDATE tracks SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, favorite: Boolean)

    @Update
    suspend fun updateTrack(track: TrackEntity)
}

