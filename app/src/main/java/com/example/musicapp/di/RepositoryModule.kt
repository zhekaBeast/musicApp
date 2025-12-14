package com.example.musicapp.di

import androidx.room.Room
import com.example.musicapp.data.NetworkClient
import com.example.musicapp.data.datasource.database.AppDatabase
import com.example.musicapp.data.network.ITunesApiService
import com.example.musicapp.data.network.RetrofitNetworkClient
import com.example.musicapp.data.repository.PlaylistsRepositoryImpl
import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "music_app.db"
        ).build()
    }

    single {
        get<AppDatabase>().tracksDao()
    }

    single {
        get<AppDatabase>().playlistsDao()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ITunesApiService> {
        get<Retrofit>().create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }
    
    single<TracksRepository> {
        TracksRepositoryImpl(get(), get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }

}
