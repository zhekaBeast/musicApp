package com.example.musicapp.di

import com.example.musicapp.ui.search.SearchViewModel
import com.example.musicapp.ui.track.TrackViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel{(trackId: String) ->
        TrackViewModel(trackId = trackId, get())
    }

    viewModel {
        SearchViewModel(get())
    }
}




