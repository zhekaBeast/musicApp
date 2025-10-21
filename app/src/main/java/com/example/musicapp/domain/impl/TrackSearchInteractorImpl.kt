package com.example.musicapp.domain.impl

import com.example.musicapp.data.dto.Track
import com.example.musicapp.domain.api.TrackSearchInteractor
import com.example.musicapp.domain.api.TracksRepository

class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor{
    override fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
}