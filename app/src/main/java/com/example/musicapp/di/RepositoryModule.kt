package com.example.musicapp.di

import com.example.musicapp.creator.Storage
import com.example.musicapp.data.network.RetrofitNetworkClient
import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.repository.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TracksRepository> {
        TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}