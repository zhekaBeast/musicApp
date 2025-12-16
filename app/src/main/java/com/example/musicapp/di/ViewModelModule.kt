package com.example.musicapp.di

import com.example.musicapp.ui.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel {
        SearchViewModel(get())
    }
}




