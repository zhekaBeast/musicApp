package com.example.musicapp.di

import com.example.musicapp.data.datasource.api.NetworkClient
import com.example.musicapp.data.datasource.mock.DatabaseMock
import com.example.musicapp.data.datasource.mock.Storage
import com.example.musicapp.data.datasource.retrofit.RetrofitNetworkClient
import com.example.musicapp.data.repository.PlaylistsRepositoryImpl
import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DatabaseMock>{
        DatabaseMock(get())
    }
    single<Storage>{
        Storage()
    }
    single<NetworkClient>{
        RetrofitNetworkClient(get())
    }
    single<TracksRepository> {
        TracksRepositoryImpl(get(), get())
    }
    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }
}