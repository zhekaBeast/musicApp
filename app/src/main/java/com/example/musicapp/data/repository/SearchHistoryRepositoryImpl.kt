package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.mock.DatabaseMock
import com.example.musicapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl( private val database: DatabaseMock): SearchHistoryRepository {

    override fun getHistoryRequests(): Flow<List<String>> {
        return database.getHistoryRequests()
    }

    override suspend fun addToHistory(word: String) {
        database.addToHistory(word = word)
    }
}