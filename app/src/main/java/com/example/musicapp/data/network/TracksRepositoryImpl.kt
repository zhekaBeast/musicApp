package com.example.musicapp.data.network

import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.dto.TracksSearchResponse
import com.example.musicapp.domain.repository.TracksRepository

class TracksRepositoryImpl(private val networkClient: NetworkClient): TracksRepository {
    override suspend fun SearchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(expression)
        if(response.resultCode==200){
            return (response as TracksSearchResponse).results.map{
                val seconds = it.trackTimeMillis/1000
                val minutes = seconds/60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds%60)
                Track(it.trackName,it.artistName,trackTime)
            }
        }else{
            return emptyList()
        }
    }
}