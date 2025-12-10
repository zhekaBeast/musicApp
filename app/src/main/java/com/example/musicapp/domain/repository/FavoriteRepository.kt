package com.example.musicapp.domain.repository

import com.example.musicapp.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteTracks(): Flow<List<Track>>
}