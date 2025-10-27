package com.example.musicapp.domain.interactor

import com.example.musicapp.domain.models.TrackModel

interface TracksInteractor {
    fun loadTrackData(trackId: String, onComplete: (TrackModel)->Unit)
}


