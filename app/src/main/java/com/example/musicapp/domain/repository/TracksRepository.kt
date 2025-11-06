package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}