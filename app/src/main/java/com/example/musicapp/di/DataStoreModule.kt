package com.example.musicapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.musicapp.data.preferences.SearchHistoryPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File


val dataStoreModule = module {
    single<DataStore<Preferences>> {
        val dataStoreDir = File(androidContext().filesDir, "datastore")
        dataStoreDir.mkdirs()

        PreferenceDataStoreFactory.create(
            produceFile = {
                File(dataStoreDir, "search_history.preferences_pb")
            }
        )
    }

    single<SearchHistoryPreferences> {
        SearchHistoryPreferences(
            dataStore = get()
        )
    }
}