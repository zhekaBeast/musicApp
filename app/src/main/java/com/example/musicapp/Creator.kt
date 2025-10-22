package com.example.musicapp

import com.example.musicapp.data.interactor.TrackPlayerImpl
import com.example.musicapp.data.interactor.TrackSearchInteractorImpl
import com.example.musicapp.data.interactor.TracksInteractorImpl
import com.example.musicapp.data.network.RetrofitNetworkClient
import com.example.musicapp.data.repository.TracksRepositoryImpl
import com.example.musicapp.domain.interactor.TrackPlayer
import com.example.musicapp.domain.interactor.TrackSearchInteractor
import com.example.musicapp.domain.interactor.TracksInteractor
import com.example.musicapp.domain.repository.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository{
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackSearchInteractor(): TrackSearchInteractor{
        return TrackSearchInteractorImpl(getTracksRepository())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl()
    }

    fun provideTrackPlayer(): TrackPlayer{
        return TrackPlayerImpl()
    }
}