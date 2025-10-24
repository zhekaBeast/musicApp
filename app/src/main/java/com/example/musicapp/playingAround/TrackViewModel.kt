package com.example.musicapp.playingAround

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackViewModel(private val trackId: String) : ViewModel() {
    init {
        Log.d("TEST", "init!")
        viewModelScope.launch {
            //tracksInteractor.searchTracks(onLoaded={ _loadingStateFlow.value=false})
        }
    }
    private var _loadingStateFlow = MutableStateFlow(true)
    fun getLoadingStateFlow(): StateFlow<Boolean> = _loadingStateFlow.asStateFlow()
}


