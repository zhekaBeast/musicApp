package com.example.musicapp.data.repository

import com.example.musicapp.data.database.dao.TracksDao
import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val tracksDao: TracksDao
) : FavoriteRepository {

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksDao.getFavoriteTracks()
            .map { entities -> entities.map { it.toDomain() } }
    }

    private fun TrackEntity.toDomain(): Track {
        val seconds = trackTimeMillis / 1000
        val minutes = seconds / 60
        val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds % 60)
        return Track(
            id = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime,
            trackTimeMillis = trackTimeMillis,
            favorite = favorite
        )
    }
}