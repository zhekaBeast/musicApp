package com.example.musicapp.ui.trackDetails

import androidx.lifecycle.ViewModel
import com.example.musicapp.data.datasource.remote.dto.Track
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException

class TrackDetailsViewModel(val tracksRepository: TracksRepository): ViewModel() {
    private val _trackState = MutableStateFlow<TrackDetailsState>(TrackDetailsState.Initial)
    val trackState = _trackState.asStateFlow()
    fun getTrackById(trackId: Long){
        try {
            _trackState.update { TrackDetailsState.Loading }
            val track = tracksRepository.getTrackById(trackId = trackId)
            _trackState.update { TrackDetailsState.Success(track) }
        }catch (e: IOException){
            _trackState.update {TrackDetailsState.Error(e.message.toString())}
        }
    }
}

sealed class TrackDetailsState(){
    object Initial: TrackDetailsState() // Cостояние экрана при первой загрузке
    object Loading: TrackDetailsState() // Cостояние экрана при начале поиска
    data class Success(val foundTrack: Track?): TrackDetailsState() // Cостояние экрана при успешном завершении поиска
    data class Error(val error: String): TrackDetailsState() // Cостояние экрана если при запросе к серверу произошла ошибка
}