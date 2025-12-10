package com.example.musicapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getHistoryRequests(): Flow<List<String>>

    suspend fun addToHistory(word: String)
}