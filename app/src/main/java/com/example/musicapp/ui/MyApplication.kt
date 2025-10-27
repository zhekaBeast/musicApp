package com.example.musicapp.ui

import android.app.Application
import com.example.musicapp.di.repositoryModule
import com.example.musicapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(repositoryModule, viewModelModule)
        }
    }
}