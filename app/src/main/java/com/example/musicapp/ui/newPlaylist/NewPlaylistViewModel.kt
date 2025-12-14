package com.example.musicapp.ui.newPlaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.repository.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class NewPlaylistViewModel(val playlistsRepository: PlaylistsRepository) : ViewModel() {
    private val _newPlaylistState = MutableStateFlow<NewPlaylistState>(NewPlaylistState.Initial)
    private var _coverImageUri = MutableStateFlow<String?>(null)
    val coverImageUri: StateFlow<String?> = _coverImageUri.asStateFlow()
    val newPlaylistState = _newPlaylistState.asStateFlow()

    fun setCoverImageUri(uri: String?) {
        _coverImageUri.value = uri
    }

    fun createNewPlayList(playlistName: String, playlistDescription: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _newPlaylistState.update { NewPlaylistState.Loading }
                playlistsRepository.addNewPlaylist(playlistName, playlistDescription, _coverImageUri.value )
                _newPlaylistState.update { NewPlaylistState.Success }
            } catch (e: IOException) {
                _newPlaylistState.update { NewPlaylistState.Error(e.message.toString()) }
            }
        }
    }
}

sealed class NewPlaylistState() {
    object Initial : NewPlaylistState()
    object Loading : NewPlaylistState()
    object Success : NewPlaylistState()
    data class Error(val error: String) : NewPlaylistState()
}