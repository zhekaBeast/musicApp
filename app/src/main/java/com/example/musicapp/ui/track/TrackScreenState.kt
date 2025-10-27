package com.example.musicapp.ui.track

import com.example.musicapp.domain.models.TrackModel

sealed class TrackScreenState {
    object Loading: TrackScreenState()
    data class Content(
        val trackModel: TrackModel,
    ): TrackScreenState()
}