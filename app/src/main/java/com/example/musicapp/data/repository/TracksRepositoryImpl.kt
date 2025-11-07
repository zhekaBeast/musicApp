package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.dto.Playlist
import com.example.musicapp.data.datasource.dto.TrackDto
import com.example.musicapp.data.datasource.dto.TracksSearchResponse
import com.example.musicapp.data.datasource.mock.DatabaseMock
import com.example.musicapp.domain.NetworkClient
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.models.TracksSearchRequest
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(private val networkClient: NetworkClient, private val db: DatabaseMock): TracksRepository {

    override suspend fun searchTracks(request: TracksSearchRequest): List<Track> {
        val response = networkClient.doRequest(request)
        if(response.resultCode==200){
            return (response as TracksSearchResponse).results.map{
                it.toDomain()
            }
        }else{
            return emptyList()
        }
    }

    override fun getTrackById(trackId: Long): Track? {
        return db.getTrackById(trackId)?.toDomain()
    }
    fun getPlaylist(id: Long): Flow<Playlist?>{
        return db.getPlaylist(id)
    }
    override fun getFavoriteTracks(): Flow<List<Track>> {
        return (db.getFavoriteTracks()).map{ it -> it.map { it.toDomain() }}
    }
    override suspend fun updateFavoriteStatus(id: Long, favorite: Boolean) : Track? {
        return db.updateFavoriteStatus(id, favorite)?.toDomain()
    }

    private fun TrackDto.toDomain(): Track {
        val seconds = this.trackTimeMillis/1000
        val minutes = seconds/60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds%60)
        return Track(
            id = this.id,
            trackName = this.trackName,
            artistName = this.artistName,
            trackTime = trackTime,
            favorite=this.favorite
        )
    }
}

