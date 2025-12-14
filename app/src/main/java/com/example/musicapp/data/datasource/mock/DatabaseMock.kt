package com.example.musicapp.data.datasource.mock

import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.data.datasource.dto.TrackDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

private const val MAX_ENTRIES = 10
private const val SEPARATOR = ","

class DatabaseMock(storage: Storage) {


    private val historyList = mutableListOf<String>()
    private val _historyFlow = MutableStateFlow(emptyList<String>())
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val _playlistsFlow = MutableStateFlow<List<Playlist>>(emptyList())
    init {
        // Инициализируем моковые данные
        _playlistsFlow.value = listOf(
            Playlist(
                id = 1,
                name = "Chill Vibes",
                description = "Relaxing tracks for unwinding",
                trackIds = listOf(1, 4, 7)
            ),
            Playlist(
                id = 2,
                name = "Workout Mix",
                description = "High-energy tracks for exercise",
                trackIds = listOf(2, 5, 8)
            ),
            Playlist(
                id = 3,
                name = "Road Trip",
                description = "Perfect for long drives",
                trackIds = listOf(3, 6, 9)
            )
        )
    }

    private val tracks = storage.getAllTracks().toMutableList<TrackDto>()



    fun updatePlaylist(newPlaylist: Playlist){
        _playlistsFlow.update { it.map{
            playlist -> if (playlist.id == newPlaylist.id) newPlaylist else playlist
        } }
    }
    fun getPlaylist(id: Long): Flow<Playlist?> =
        _playlistsFlow.map { playlists -> playlists.find { it.id == id } }

    fun getHistoryRequests(): Flow<List<String>> = _historyFlow


    suspend fun addToHistory(word: String) {
        historyList.removeAll { it.equals(word, ignoreCase = true) }

        historyList.add(word)

        if (historyList.size > 10) {
            historyList.removeAt(0)
        }
        _historyFlow.emit(historyList.toList())
    }

    suspend private fun notifyHistoryChanged() {
        _historyUpdates.emit(Unit)
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = _playlistsFlow.asStateFlow()

    fun addNewPlaylist(namePlaylist: String, description: String) {
        _playlistsFlow.update{ playlists ->
                playlists + Playlist(
                id = playlists.size.toLong() + 1,
                name = namePlaylist,
                description = description
                )
        }
    }

    fun deletePlaylistById(id: Long) {
        _playlistsFlow.update { it.filter { playlist-> playlist.id!=id }}
    }

    fun getTrackById(trackId: Long): TrackDto?{
        return tracks.find{it.id == trackId}?.copy()
    }

    fun insertTrack(track: TrackDto) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<TrackDto>> = flow {
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun insertSongToPlaylist(trackId: Long, playlistId: Long) {
        _playlistsFlow.update { currentPlaylists ->
            currentPlaylists.map { playlist ->
                if (playlist.id == playlistId && !playlist.trackIds.contains(trackId)) {
                    playlist.copy(trackIds = playlist.trackIds + trackId)
                } else {
                    playlist
                }
            }
        }
    }

    fun deleteSongFromPlaylist(trackId: Long, playlistId: Long) {
        _playlistsFlow.update { currentPlaylists ->
            currentPlaylists.map { playlist ->
                if (playlist.id == playlistId) {
                    playlist.copy(trackIds = playlist.trackIds.filter { it != trackId })
                } else {
                    playlist
                }
            }
        }
    }

    fun updateFavoriteStatus(id: Long, favorite: Boolean): TrackDto?{
        val track = tracks.find{it.id==id}
        track?.favorite = favorite
        return track?.copy()
    }
}











