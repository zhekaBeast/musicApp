package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.models.TracksSearchRequest
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(request: TracksSearchRequest): List<Track>

    fun getTrackById(trackId: Long): Track?

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun updateFavoriteStatus(id: Long, favorite: Boolean) : Track?


}