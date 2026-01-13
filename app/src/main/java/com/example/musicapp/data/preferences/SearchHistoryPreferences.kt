package com.example.musicapp.data.preferences


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {

    companion object {
        private val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
        private val MAX_ENTRIES = 10
        private val SEPARATOR = ","
    }

    fun addEntry(word: String) {
        if (word.isEmpty()) {
            return
        }

        coroutineScope.launch {
            dataStore.updateData { preferences ->
                val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()
                val history = historyString.split(SEPARATOR)
                    .filter { it.isNotEmpty() }
                    .toMutableList()

                history.removeAll { it.equals(word, ignoreCase = true) }

                history.add(0, word)

                val limitedHistory = history.take(MAX_ENTRIES)

                preferences.toMutablePreferences().apply {
                    this[SEARCH_HISTORY_KEY] = limitedHistory.joinToString(SEPARATOR)
                }
            }
        }
    }



    suspend fun getEntries(): Flow<List<String>> {
        return dataStore.data
            .map { preferences ->
                val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()
                historyString.split(SEPARATOR)
                    .filter { it.isNotBlank() }
                    .reversed()
            }
    }
}