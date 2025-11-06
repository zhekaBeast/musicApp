package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.models.TracksSearchRequest

interface TracksRepository {
    suspend fun searchTracks(request: TracksSearchRequest): List<Track>
}