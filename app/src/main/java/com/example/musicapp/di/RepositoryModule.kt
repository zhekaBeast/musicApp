package com.example.musicapp.di

import com.example.musicapp.data.datasource.remote.mock.DatabaseMock
import com.example.musicapp.data.datasource.remote.mock.Storage
import com.example.musicapp.data.datasource.remote.retrofit.RetrofitNetworkClient
import com.example.musicapp.data.repository.PlaylistsRepositoryImpl
import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.repository.PlaylistsRepository
import com.example.musicapp.domain.repository.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TracksRepository> {
        TracksRepositoryImpl(RetrofitNetworkClient(Storage()), DatabaseMock())
    }
    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl()
    }
}