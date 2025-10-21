package com.example.musicapp.domain.api

import com.example.musicapp.data.dto.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}