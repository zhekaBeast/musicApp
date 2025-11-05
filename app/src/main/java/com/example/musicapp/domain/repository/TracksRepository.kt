package com.example.musicapp.domain.repository

import com.example.musicapp.data.datasource.dto.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun SearchTracks(expression: String): List<Track>

    fun getTrackById(trackId: Long): Track?

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun updateFavoriteStatus(id: Long, favorite: Boolean) : Track?

}