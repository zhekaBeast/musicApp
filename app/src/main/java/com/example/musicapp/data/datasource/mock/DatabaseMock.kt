package com.example.musicapp.data.datasource.mock

import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.data.datasource.dto.TrackDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow


class DatabaseMock(storage: Storage) {


    private val historyList = mutableListOf<String>()
    private val _historyFlow = MutableStateFlow(emptyList<String>())
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()

    private val tracks = storage.getAllTracks().toMutableList<TrackDto>()



    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        delay(500)
        emit(playlists.find { it.id == id })
    }

    fun getHistoryRequests(): Flow<List<String>> = _historyFlow


    suspend fun addToHistory(word: String) {
        historyList.removeAll { it.equals(word, ignoreCase = true) }

        historyList.add(word)

        if (historyList.size > 5) {
            historyList.removeAt(0)
        }
        _historyFlow.emit(historyList.toList())
    }

    suspend private fun notifyHistoryChanged() {
        _historyUpdates.emit(Unit)
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500)
        emit(playlists.toList())
    }

    fun addNewPlaylist(namePlaylist: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = namePlaylist,
                description = description
            )
        )
    }

    fun deletePlaylistById(id: Long) {
        playlists.removeIf { it.id == id }
    }

    fun getTrackById(trackId: Long): TrackDto?{
        return tracks.find{it.id == trackId}?.copy()
    }

    fun insertTrack(track: TrackDto) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<TrackDto>> = flow {
        delay(300)
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun insertSongToPlaylist(
        trackId: Long,
        playlistId: Long
    ) {
        playlists.find{it.id==playlistId}?.trackIds?.add(trackId)
    }

    fun deleteSongFromPlaylist(trackId: Long, playlistId: Long) {
        val playlist = playlists.find { it.id == playlistId }
        playlist?.trackIds?.remove(trackId)
    }

    fun updateFavoriteStatus(id: Long, favorite: Boolean): TrackDto?{
        val track = tracks.find{it.id==id}
        track?.favorite = favorite
        return track?.copy()
    }
}











