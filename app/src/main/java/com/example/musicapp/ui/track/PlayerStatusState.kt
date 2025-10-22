package com.example.musicapp.ui.track

sealed class PlayerStatusState {
    object Initial : PlayerStatusState()
    object Loading : PlayerStatusState()

    data class PlaybackState(
        val isPlaying: Boolean,
        val progress: Float = 0f,
        val currentPosition: Long = 0L,
        val duration: Long = 0L,
        val isBuffering: Boolean = false
    ) : PlayerStatusState()

    data class Error(val message: String) : PlayerStatusState()
}