package com.example.musicapp.ui.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.Creator
import com.example.musicapp.domain.interactor.TrackPlayer
import com.example.musicapp.domain.interactor.TracksInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
    private val trackPlayer: TrackPlayer,
) : ViewModel() {
    private val _trackScreenState = MutableStateFlow<TrackScreenState>(TrackScreenState.Loading)
    val trackScreenState = _trackScreenState.asStateFlow()
    private val _playerStatusState = MutableStateFlow<PlayerStatusState>(PlayerStatusState.Loading)

    val playerStatusState = _playerStatusState.asStateFlow()


    init {
        tracksInteractor.loadTrackData(
            trackId = trackId,
            onComplete = { trackModel ->
                // 1
                _trackScreenState.value = TrackScreenState.Content(trackModel)
            }
        )
    }

    fun play() {

        trackPlayer.play(
            trackId = trackId,
            // 1
            statusObserver = object : TrackPlayer.StatusObserver {
                override fun onProgress(progress: Float) {

                    // 2 TODO
                    //_playerStatusState.value = _playerStatusState.value.copy(progress = progress)
                }
                override fun onStop() {
                    // 3 TODO
                    //_playerStatusState.value = getCurrentPlayStatus().copy(isPlaying = false)
                }
                override fun onPlay() {
                    // 4 TODO
                    //_playerStatusState.value = getCurrentPlayStatus().copy(isPlaying = true)
                }
            },
        )
    }

    // 5
    fun pause() {
        trackPlayer.pause(trackId)
    }

    // 6
    override fun onCleared() {
        trackPlayer.release(trackId)
    }

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val interactor = Creator.provideTracksInteractor()
                val trackPlayer = Creator.provideTrackPlayer()
                TrackViewModel(
                    trackId,
                    interactor,
                    trackPlayer
                )
            }
        }
    }
}