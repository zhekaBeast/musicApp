package com.example.musicapp.domain.api

import com.example.musicapp.data.dto.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
}