package com.example.musicapp.domain.repository

import com.example.musicapp.data.network.Track

interface TracksRepository {
    suspend fun SearchTracks(expression: String): List<Track>
}