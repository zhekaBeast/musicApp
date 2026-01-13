package com.example.musicapp.di

import com.example.musicapp.ui.favorite.FavoriteViewModel
import com.example.musicapp.ui.newPlaylist.NewPlaylistViewModel
import com.example.musicapp.ui.playlist.PlaylistViewModel
import com.example.musicapp.ui.playlists.PlaylistsViewModel
import com.example.musicapp.ui.search.SearchViewModel
import com.example.musicapp.ui.trackDetails.TrackDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        PlaylistsViewModel(get())
    }

    viewModel {
        TrackDetailsViewModel(get(), get())
    }

    viewModel {
        NewPlaylistViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get())
    }

    viewModel {
        PlaylistViewModel(get(), get())
    }
}




