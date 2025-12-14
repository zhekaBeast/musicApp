package com.example.musicapp.data.repository

import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.datasource.database.dao.TracksDao
import com.example.musicapp.data.datasource.database.entity.TrackEntity
import com.example.musicapp.data.datasource.database.entity.toDomain
import com.example.musicapp.data.datasource.dto.TracksSearchResponse
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.models.TracksSearchRequest
import com.example.musicapp.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val tracksDao: TracksDao
) : TracksRepository {

    override suspend fun searchTracks(request: TracksSearchRequest): List<Track> {
        val response = networkClient.doRequest(request)
        if (response.resultCode != 200 || response !is TracksSearchResponse) {
            return emptyList()
        }

        val result = mutableListOf<Track>()

        response.results.forEach { dto ->
            // Preserve favorite flag from local DB if present
            val existing = tracksDao.getTrackById(dto.id)
            val favorite = existing?.favorite ?: false

            val entity = TrackEntity(
                id = dto.id,
                trackName = dto.trackName,
                artistName = dto.artistName,
                trackTimeMillis = dto.trackTimeMillis,
                favorite = favorite,
                artworkUrl = dto.artworkUrl
            )

            tracksDao.insertTrack(entity)

            result.add(entity.toDomain())
        }

        return result
    }

    override fun getTrackById(trackId: Long): Track? {
        val entity = tracksDao.getTrackById(trackId) ?: return null
        return entity.toDomain()
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksDao.getFavoriteTracks()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun updateFavoriteStatus(id: Long, favorite: Boolean): Track? {
        tracksDao.updateFavoriteStatus(id, favorite)
        val updated = tracksDao.getTrackById(id) ?: return null
        return updated.toDomain()
    }

}

