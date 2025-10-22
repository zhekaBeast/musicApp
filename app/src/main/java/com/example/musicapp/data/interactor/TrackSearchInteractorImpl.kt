package com.example.musicapp.data.interactor

import com.example.musicapp.data.dto.Track
import com.example.musicapp.domain.interactor.TrackSearchInteractor
import com.example.musicapp.domain.repository.TracksRepository

class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor{
    override fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
}