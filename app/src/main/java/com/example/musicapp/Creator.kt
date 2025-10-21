package com.example.musicapp

import com.example.musicapp.data.TracksRepositoryImpl
import com.example.musicapp.data.network.RetrofitNetworkClient
import com.example.musicapp.domain.api.TrackSearchInteractor
import com.example.musicapp.domain.api.TracksRepository
import com.example.musicapp.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(): TracksRepository{
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackSearchInteractor(): TrackSearchInteractor{
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}