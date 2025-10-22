package com.example.musicapp.domain.repository

import com.example.musicapp.data.dto.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}