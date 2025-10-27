package com.example.musicapp.ui.track

import androidx.lifecycle.ViewModel
import com.example.musicapp.domain.interactor.TrackPlayer

class TrackViewModel(
    private val trackId: String,
    private val trackPlayer: TrackPlayer,
) : ViewModel() {
}