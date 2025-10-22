package com.example.musicapp.playingAround

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.Creator
import com.example.musicapp.domain.interactor.TrackSearchInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackViewModel(private val trackId: String, private val tracksInteractor: TrackSearchInteractor) : ViewModel() {
    init {
        Log.d("TEST", "init!")
        viewModelScope.launch {
            //tracksInteractor.searchTracks(onLoaded={ _loadingStateFlow.value=false})
        }
    }
    private var _loadingStateFlow = MutableStateFlow(true)
    fun getLoadingStateFlow(): StateFlow<Boolean> = _loadingStateFlow.asStateFlow()

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TrackViewModel(
                    trackId,
                    Creator.provideTrackSearchInteractor()
                )
            }
        }
    }
}


