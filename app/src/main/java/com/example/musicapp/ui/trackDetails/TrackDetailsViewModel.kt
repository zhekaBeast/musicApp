package com.example.musicapp.ui.trackDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class TrackDetailsViewModel(val tracksRepository: TracksRepository): ViewModel() {
    private val _trackState = MutableStateFlow<TrackDetailsState>(TrackDetailsState.Initial)
    val trackState = _trackState.asStateFlow()
    fun getTrackById(trackId: Long){
        try {
            _trackState.update { TrackDetailsState.Loading }
            val track = tracksRepository.getTrackById(trackId = trackId)
            if(track==null) {
                _trackState.update { TrackDetailsState.NotFound }
            } else{
                _trackState.update { TrackDetailsState.Success(track) }
            }
        }catch (e: IOException){
            _trackState.update {TrackDetailsState.Error(e.message.toString())}
        }
    }

    fun toggleFavorite(){
        val currentState = _trackState.value
        if (currentState is TrackDetailsState.Success) {
            viewModelScope.launch {
                try {
                    _trackState.update { TrackDetailsState.Loading }
                    val updatedTrack = tracksRepository.updateFavoriteStatus(currentState.foundTrack!!.id,
                        !currentState.foundTrack.favorite)
                    if(updatedTrack==null) {
                        _trackState.update { TrackDetailsState.NotFound }
                    } else{
                        _trackState.update { TrackDetailsState.Success(updatedTrack) }
                    }
                } catch (e: IOException) {
                    _trackState.update { TrackDetailsState.Error(e.message.toString()) }
                }
            }
        }
    }

    fun addTrackInPlaylist(playlistId: Long) {
    }

}

sealed class TrackDetailsState(){
    object Initial: TrackDetailsState() // Cостояние экрана при первой загрузке
    object Loading: TrackDetailsState() // Cостояние экрана при начале поиска

    object NotFound : TrackDetailsState()
    data class Success(val foundTrack: Track): TrackDetailsState() // Cостояние экрана при успешном завершении поиска
    data class Error(val error: String): TrackDetailsState() // Cостояние экрана если при запросе к серверу произошла ошибка
}