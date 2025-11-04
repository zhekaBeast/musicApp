package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.remote.api.NetworkClient
import com.example.musicapp.data.datasource.remote.dto.Playlist
import com.example.musicapp.data.datasource.remote.dto.Track
import com.example.musicapp.data.datasource.remote.dto.TracksSearchResponse
import com.example.musicapp.data.datasource.remote.mock.DatabaseMock
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(private val networkClient: NetworkClient, private val db: DatabaseMock): TracksRepository {

    override suspend fun SearchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(expression)
        if(response.resultCode==200){
            return (response as TracksSearchResponse).results.map{
                val seconds = it.trackTimeMillis/1000
                val minutes = seconds/60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds%60)
                Track(trackName =  it.trackName, artistName =  it.artistName, trackTime =  trackTime)
            }
        }else{
            return emptyList()
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return db.getTrackByNameAndArtist(track)
    }
    fun getPlaylist(id: Long): Flow<Playlist?>{
        return db.getPlaylist(id)
    }
    override fun getFavoriteTracks(): Flow<List<Track>> {
        return db.getFavoriteTracks()
    }

    override suspend fun insertSongToPlaylist(
        track: Track,
        playlistId: Long
    ) {
        db.insertSongToPlaylist(track, playlistId)
    }

    override suspend fun deleteSongFromPlaylist(trackId: Long, playlistId: Long) {
        db.deleteSongFromPlaylist(trackId, playlistId)
    }



    override suspend fun updateFavoriteStatus(id: Long, favorite: Boolean) {
        db.updateFavoriteStatus(id, favorite)
    }

}

