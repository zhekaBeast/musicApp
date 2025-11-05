package com.example.musicapp.di

import com.example.musicapp.ui.playlists.PlaylistViewModel
import com.example.musicapp.ui.search.SearchViewModel
import com.example.musicapp.ui.trackDetails.TrackDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        PlaylistViewModel(get(), get())
    }

    viewModel {
        TrackDetailsViewModel(get())
    }
}




