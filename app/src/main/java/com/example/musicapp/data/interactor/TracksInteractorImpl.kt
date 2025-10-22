package com.example.musicapp.data.interactor

import com.example.musicapp.domain.interactor.TracksInteractor
import com.example.musicapp.domain.models.TrackModel

class TracksInteractorImpl : TracksInteractor{
    override
    fun loadTrackData(trackId: String, onComplete: (TrackModel) -> Unit) {
        val tm = TrackModel("1","1","1","1")
        onComplete(tm)
    }
}