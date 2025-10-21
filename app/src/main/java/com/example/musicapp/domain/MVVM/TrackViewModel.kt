package com.example.musicapp.domain.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.domain.api.TrackSearchInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TrackSearchInteractor,
) : ViewModel() {
    private val _trackScreenState = MutableStateFlow(TrackScreenState.Loading)
    val trackScreenState = _trackScreenState.asStateFlow()

    init {
        tracksInteractor.loadTrackData(
            trackId = trackId,
            onComplete = { trackModel ->
                // 1
                _trackScreenState.value = TrackScreenState.Content(trackModel)
            }
        )
    }

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = (this[APPLICATION_KEY] as MyApplication).provideTracksInteractor()

                TrackViewModel(
                    trackId,
                    interactor,
                )
            }
        }
    }
}