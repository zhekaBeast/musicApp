package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.mock.DatabaseMock
import com.example.musicapp.domain.models.Track
import com.example.musicapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val database: DatabaseMock,
) : FavoriteRepository {

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
            .map { trackDTOList -> trackDTOList.map { it.toDomain() } }
    }
}