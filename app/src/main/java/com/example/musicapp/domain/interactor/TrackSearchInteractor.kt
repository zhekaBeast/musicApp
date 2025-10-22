package com.example.musicapp.domain.interactor

import com.example.musicapp.data.dto.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
}